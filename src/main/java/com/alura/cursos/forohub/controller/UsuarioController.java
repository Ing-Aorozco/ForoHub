package com.alura.cursos.forohub.controller;

import com.alura.cursos.forohub.domain.usuarios.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@SecurityRequirement(name = "bearer-key")
@RequestMapping("/usuario")
public class UsuarioController {

  @Autowired
  private UsuarioService service;

  @Operation(
    summary = "Crear nuevo Usuario",
    description = " Este endpoint Permite crear un nuevo Usuario",
    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
      content = @Content(
        mediaType = "application/json",
        schema = @Schema(implementation = DatosGuardarUsuario.class)
      )
    ),
    responses = {
      @ApiResponse(responseCode = "201", description = "Usuario creado successfully"),
      @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos")
    }
  )
  @PostMapping
  @Transactional
  public ResponseEntity<DatosRespuestaUsuario> createUser(
    @RequestBody @Valid DatosGuardarUsuario dataRecordsUsers,
    UriComponentsBuilder uriComponentsBuilder
  ) {
    DatosRespuestaUsuario response = service.createUser(dataRecordsUsers);
    URI url = uriComponentsBuilder.path("/usuario/{id}").buildAndExpand(response.id()).toUri();

    return ResponseEntity.created(url).body(response);
  }

  @Operation(
    summary = "Lista de usuarios",
    description = "Este endpoint muestra el listado de Usuarios."
  )
  @GetMapping
  public ResponseEntity<List<DatosListarUsuario>> listUser() {
    List<DatosListarUsuario> dataListUsers = service.getUsers();
    return ResponseEntity.ok(dataListUsers);
  }

  @Operation(
    summary = "Actualizar Usuario",
    description = "Este endpoint Permite Actualizar la informacion del  Usuario",
    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
      content = @Content(
        mediaType = "application/json",
        schema = @Schema(implementation = DatosActualizarUsuario.class)
      )
    ),
    responses = {
      @ApiResponse(responseCode = "200", description = "Usuario Acualizado successfully"),
      @ApiResponse(responseCode = "400", description = "Datos de entrada invalidosa")
    }
  )
  @PutMapping
  @Transactional
  public ResponseEntity<DatosRespuestaUsuario> updateUser(@RequestBody @Valid DatosActualizarUsuario dataUpdateUser) {
    var user = service.updateUser(dataUpdateUser);
    return ResponseEntity.ok(user);
  }

  @Operation(
    summary = "Eliminar Usuario",
    description = "Este endpoint Permite Eliminar un   Usuario."
  )
  @DeleteMapping("/{id}")
  public ResponseEntity deletedUser(@PathVariable Long id) {
    service.deletedUser(id);
    return ResponseEntity.noContent().build();
  }
}