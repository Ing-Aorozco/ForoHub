package com.alura.cursos.forohub.domain.usuarios;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DatosGuardarUsuario(
  @NotBlank
  String name,
  @NotBlank
  @Email
  String email,
  @NotBlank
  String password
) {
}