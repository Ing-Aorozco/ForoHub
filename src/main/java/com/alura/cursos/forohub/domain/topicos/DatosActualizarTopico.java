package com.alura.cursos.forohub.domain.topicos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosActualizarTopico(
  @NotNull
  Long id,
  @NotBlank
  String title,
  @NotBlank
  String message
) {
}
