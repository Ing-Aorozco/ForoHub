package com.alura.cursos.forohub.domain.respuesta;

public record DatosRespuesta(
  Long id,
  String message,
  Boolean solved,
  Long idTopic,
  Long idUser
) {
  public DatosRespuesta(Respuesta answer) {
    this(
      answer.getId(),
      answer.getMensaje(),
      answer.getSolucionado(),
      answer.getUsuario().getId(),
      answer.getTopicos().getId()
    );
  }
}
