package com.alura.cursos.forohub.domain.respuesta;

import java.time.LocalDateTime;

public record DatosListarRespuesta(
  Long id,
  String message,
  Boolean solved,
  Long idTopic,
  Long idUser,
  LocalDateTime dateCreation
) {
  public DatosListarRespuesta(Respuesta answer) {
    this(
      answer.getId(),
      answer.getMensaje(),
      answer.getSolucionado(),
      answer.getUsuario().getId(),
      answer.getTopicos().getId(),
      answer.getFechaCreacion()
    );
  }
}
