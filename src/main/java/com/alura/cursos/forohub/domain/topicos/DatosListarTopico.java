package com.alura.cursos.forohub.domain.topicos;

import java.time.LocalDateTime;

public record DatosListarTopico(
  Long id,
  String title,
  String message,
  LocalDateTime dateCreation,
  TopicoStatus status,
  Long idUser,
  Long idCourse
) {

  public DatosListarTopico(Topico topic) {
    this(
      topic.getId(),
      topic.getTitulo(),
      topic.getMensaje(),
      topic.getFechaCreacion(),
      topic.getStatus(),
      topic.getUsuario().getId(),
      topic.getCurso().getId()
    );
  }
}