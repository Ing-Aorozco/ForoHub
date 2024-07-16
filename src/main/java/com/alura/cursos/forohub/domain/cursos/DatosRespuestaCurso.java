package com.alura.cursos.forohub.domain.cursos;

public record DatosRespuestaCurso(
  Long id,
  String name,
  Categoria category,
  Boolean isDeleted

) {

  public DatosRespuestaCurso(curso course) {
    this(
      course.getId(),
      course.getNombre(),
      course.getCategoria(),
      course.getEstado()
    );
  }
}
