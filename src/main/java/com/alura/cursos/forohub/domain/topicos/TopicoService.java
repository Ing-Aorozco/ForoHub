package com.alura.cursos.forohub.domain.topicos;

import com.alura.cursos.forohub.domain.cursos.CursoRepository;
import com.alura.cursos.forohub.domain.usuarios.UsuarioRepository;
import com.alura.cursos.forohub.infra.errors.IntegrityValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class TopicoService {
  @Autowired
  private TopicoRepository topicRepository;

  @Autowired
  private UsuarioRepository userRepository;

  @Autowired
  private CursoRepository courseRepository;

  public DatosRespuestaTopico createTopic(DatosCrearTopico datosCrearTopico) {

    if (!userRepository.findById(datosCrearTopico.usuario_id()).isPresent()) {
      throw new IntegrityValidation("id user not found");
    }

    if (!courseRepository.findById(datosCrearTopico.curso_id()).isPresent()) {
      throw new IntegrityValidation("id course not found");
    }

    if (topicRepository.findByTitulo(datosCrearTopico.titulo()).isPresent()) {
      throw new IntegrityValidation("There already exists a topic with the same titulo");
    }

    if (topicRepository.findByMensaje(datosCrearTopico.mensaje()).isPresent()) {
      throw new IntegrityValidation("There already exists a topic with the same mensaje");
    }

    var user = userRepository.findById(datosCrearTopico.usuario_id()).get();
    var course = courseRepository.findById(datosCrearTopico.curso_id()).get();

    var topic = new Topico(
      datosCrearTopico.titulo(),
      datosCrearTopico.mensaje(),
      user,
      course
    );

    topicRepository.save(topic);
    return new DatosRespuestaTopico(topic);
  }

  public Page<DatosListarTopico> getTopics(Pageable pageable) {
    Page<Topico> topicPage = topicRepository.findByEstadoFalse(pageable);
    return topicPage.map(DatosListarTopico::new);
  }

  public Page<DatosListarTopico> getTopicsByCourseNameAndDateCreation(String courseName, String year, Pageable pageable) {
    var course = courseRepository.findByNombre(courseName);
    if (course == null) {
      throw new IntegrityValidation("Course does not exist");
    }

    LocalDateTime startDate;
    try {
      int yearNumber = Integer.parseInt(year);
      startDate = LocalDateTime.of(yearNumber, 1, 1, 0, 0);
    } catch (NumberFormatException e) {
      throw new IntegrityValidation("Invalid year format");
    }

    var topics = topicRepository.findByCursoAndFechaCreacionAfter(course, startDate, pageable);

    var dataListTopics = topics.getContent().stream()
      .map(DatosListarTopico::new)
      .collect(Collectors.toList());

    return new PageImpl<>(dataListTopics, pageable, topics.getTotalElements());
  }

  public DatosRespuestaTopico detailsTopic(@PathVariable Long id) {
    Topico topic = topicRepository.getReferenceById(id);
    return new DatosRespuestaTopico(topic);
  }

  public DatosRespuestaTopico updateTopic(DatosActualizarTopico dataUpdateTopic) {
    if (!topicRepository.findById(dataUpdateTopic.id()).isPresent()) {
      throw new IntegrityValidation("id topic not found");
    }

    Topico topic = topicRepository.getReferenceById(dataUpdateTopic.id());
    topic.putData(dataUpdateTopic);

    return new DatosRespuestaTopico(topic);
  }

  public void deletedTopic(Long id) {
    Topico topic = topicRepository.findById(id)
      .orElseThrow(() -> new IntegrityValidation("Topic not found with id"));

    topic.deletedTopic();
    topicRepository.save(topic);
  }
}

