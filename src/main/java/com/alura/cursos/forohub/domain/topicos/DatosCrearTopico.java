package com.alura.cursos.forohub.domain.topicos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosCrearTopico(
  @NotBlank
  String titulo,
  @NotBlank
  String mensaje,
  @NotNull
  Long usuario_id,
  @NotNull
  Long curso_id
) {
}
