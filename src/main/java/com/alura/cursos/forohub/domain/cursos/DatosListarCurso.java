package com.alura.cursos.forohub.domain.cursos;

public record DatosListarCurso(
  Long id,
  String nombre,
  Categoria categoria
) {
  public DatosListarCurso(curso course) {
    this(
      course.getId(),
      course.getNombre(),
      course.getCategoria()
    );
  }
}
