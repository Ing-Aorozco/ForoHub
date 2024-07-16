package com.alura.cursos.forohub.domain.usuarios;

import com.alura.cursos.forohub.infra.errors.IntegrityValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
  @Autowired
  private UsuarioRepository userRepository;
  private BCryptPasswordEncoder passwordEncoder;

  public UsuarioService(UsuarioRepository userRepository) {
    this.passwordEncoder = new BCryptPasswordEncoder();
  }
  public DatosRespuestaUsuario createUser(DatosGuardarUsuario dataRecordsUsers) {
    String hashedPassword = passwordEncoder.encode(dataRecordsUsers.password());

    var user = new Usuario(
      dataRecordsUsers.name(),
      dataRecordsUsers.email(),
      hashedPassword
    );

    userRepository.save(user);
    return new DatosRespuestaUsuario(user);
  }

  public List<DatosListarUsuario> getUsers() {
    List<Usuario> activeUsers = userRepository.findByIsActiveTrue();
    return activeUsers.stream()
      .map(DatosListarUsuario::new)
      .collect(Collectors.toList());
  }

  public DatosRespuestaUsuario updateUser(DatosActualizarUsuario dataUpdateUser) {
    if (!userRepository.findById(dataUpdateUser.id()).isPresent()) {
      throw new IntegrityValidation("id user not found");
    }

    Usuario user = userRepository.getReferenceById(dataUpdateUser.id());

    if (dataUpdateUser.password() != null) {
      String hashedPassword = passwordEncoder.encode(dataUpdateUser.password());
      user.setPassword(hashedPassword);
    }

    user.setName(dataUpdateUser.name());
    user.setEmail(dataUpdateUser.email());
    userRepository.save(user);

    return new DatosRespuestaUsuario(user);
  }

  public void deletedUser(Long id) {
    Usuario user = userRepository.findById(id)
      .orElseThrow(() -> new IntegrityValidation("User not found with id"));

    user.deletedUser();
    userRepository.save(user);
  }
}
