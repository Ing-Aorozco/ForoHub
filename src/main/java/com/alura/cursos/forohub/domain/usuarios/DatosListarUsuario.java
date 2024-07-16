package com.alura.cursos.forohub.domain.usuarios;

public record DatosListarUsuario(
  Long id,
  String name,
  String email
) {

  public DatosListarUsuario(Usuario user) {
    this(
      user.getId(),
      user.getName(),
      user.getEmail()
    );
  }
}
