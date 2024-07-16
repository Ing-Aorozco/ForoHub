package com.alura.cursos.forohub.controller;

import com.alura.cursos.forohub.domain.usuarios.Usuario;
import com.alura.cursos.forohub.domain.usuarios.UsuarioAuthenticationData;
import com.alura.cursos.forohub.infra.security.DataJWTtoken;
import com.alura.cursos.forohub.infra.security.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private TokenService tokenService;

  @Operation(
    summary = "Autenticar usuario",
    description = "Permite autenticar un suario y obtener el JWT token.",
    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
      content = @Content(
        mediaType = "application/json",
        schema = @Schema(implementation = UsuarioAuthenticationData.class)
      )
    ),
    responses = {
      @ApiResponse(responseCode = "200", description = "Autenticacion Exitosa!, Devuelve el JWT token"),
      @ApiResponse(responseCode = "401", description = "Usuario o contraseña incorrectos")
    }
  )
  @PostMapping
  public ResponseEntity authenticateUser(@RequestBody @Valid UsuarioAuthenticationData userAuthenticationData) {
    Authentication authToken = new UsernamePasswordAuthenticationToken(
      userAuthenticationData.email(),
      userAuthenticationData.password()
    );
    var userAuthenticated = authenticationManager.authenticate(authToken);
    var JWTtoken = tokenService.generToken((Usuario) userAuthenticated.getPrincipal());
    return ResponseEntity.ok(new DataJWTtoken(JWTtoken));
  }
}