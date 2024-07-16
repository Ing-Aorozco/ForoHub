package com.alura.cursos.forohub.domain.usuarios;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosActualizarUsuario(
  @NotNull
  Long id,
  @NotBlank
  String name,
  @NotBlank
  String email,
  @NotBlank
  String password
) {
}
