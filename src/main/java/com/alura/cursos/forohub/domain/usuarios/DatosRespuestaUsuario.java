package com.alura.cursos.forohub.domain.usuarios;

public record DatosRespuestaUsuario(
  Long id,
  String name,
  String email,
  String password
) {
  public DatosRespuestaUsuario(Usuario user) {
    this(
      user.getId(),
      user.getName(),
      user.getEmail(),
      user.getPassword()
    );
  }
}
