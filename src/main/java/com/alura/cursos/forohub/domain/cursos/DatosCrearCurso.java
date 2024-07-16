package com.alura.cursos.forohub.domain.cursos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosCrearCurso(
  @NotBlank
  String nombre,
  @NotNull
  Categoria categoria

) {
}
