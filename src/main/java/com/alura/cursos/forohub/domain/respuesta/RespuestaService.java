package com.alura.cursos.forohub.domain.respuesta;

import com.alura.cursos.forohub.domain.topicos.TopicoRepository;
import com.alura.cursos.forohub.domain.topicos.TopicoStatus;
import com.alura.cursos.forohub.domain.usuarios.UsuarioRepository;
import com.alura.cursos.forohub.infra.errors.IntegrityValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class RespuestaService {

  @Autowired
  private TopicoRepository topicRepository;

  @Autowired
  private UsuarioRepository userRepository;

  @Autowired
  private RespuestaRepository answerRepository;

  public DatosRespuesta createAnswer(DatosCrearRespuesta dataCreateAnswer) {

    if (!userRepository.findById(dataCreateAnswer.usuario_id()).isPresent()) {
      throw new IntegrityValidation("id user not found");
    }

    if (!topicRepository.findById(dataCreateAnswer.topico_id()).isPresent()) {
      throw new IntegrityValidation("id topic not found");
    }

    var user = userRepository.findById(dataCreateAnswer.usuario_id()).get();
    var topic = topicRepository.findById(dataCreateAnswer.topico_id()).get();

    var answer = new Respuesta(
      dataCreateAnswer.mensaje(),
      dataCreateAnswer.solucionado(),
      topic,
      user
    );
    answerRepository.save(answer);

    topic.setStatus(TopicoStatus.ANSWERED);
    topicRepository.save(topic);

    return new DatosRespuesta(answer);
  }

  public Page<DatosListarRespuesta> getAnswer(Pageable pageable) {
    Page<Respuesta> answerPage = answerRepository.findByEstadoFalse(pageable);
    return answerPage.map(DatosListarRespuesta::new);
  }

  public DatosRespuesta detailsAnswer(@PathVariable Long id) {
    Respuesta answer = answerRepository.getReferenceById(id);
    return new DatosRespuesta(answer);
  }

  public DatosRespuesta updateAnswer(DatosActualizarRespuesta dataUpdateAnswer) {
    if(!answerRepository.findById(dataUpdateAnswer.id()).isPresent()) {
      throw new IntegrityValidation("id answer not found");
    }

    Respuesta answer = answerRepository.getReferenceById(dataUpdateAnswer.id());
    answer.putData(dataUpdateAnswer);

    return new DatosRespuesta(answer);
  }

  public void deletedAnswer(Long id) {
    Respuesta answer = answerRepository.findById(id)
      .orElseThrow(() -> new IntegrityValidation("Topic not found with id"));

    answer.deletedAnswer();
    answerRepository.save(answer);
  }

}
