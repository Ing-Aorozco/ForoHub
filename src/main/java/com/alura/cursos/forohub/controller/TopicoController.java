package com.alura.cursos.forohub.controller;


import com.alura.cursos.forohub.domain.topicos.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topico")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

  @Autowired
  private TopicoService service;

  @Operation(
    summary = "Crear topico",
    description = "Este endpoint Permite Crear un Topico",
    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
      content = @Content(
        mediaType = "application/json",
        schema = @Schema(implementation = DatosCrearTopico.class)
      )
    ),
    responses = {
      @ApiResponse(responseCode = "201", description = "Topico creado successfully"),
      @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos")
    }
  )
  @PostMapping
  @Transactional
  public ResponseEntity<DatosRespuestaTopico> createTopic(
    @RequestBody @Valid DatosCrearTopico dataCreateTopic,
    UriComponentsBuilder uriComponentsBuilder
  ) {
    DatosRespuestaTopico response = service.createTopic(dataCreateTopic);
    URI url = uriComponentsBuilder.path("/topico/{id}").buildAndExpand(response.id()).toUri();

    return ResponseEntity.created(url).body(response);
  }

  @Operation(
    summary = "Listar topicos",
    description = "Este endpoint Permite Listar los Topicos"
  )
  @GetMapping
  public ResponseEntity<Page<DatosListarTopico>> listTopics(
    @Parameter(description = "Numero de Paginas")
    @PageableDefault(size = 10, sort = "fechaCreacion")
    Pageable pageable
  ) {
    Page<DatosListarTopico> dataListTopicPage = service.getTopics(pageable);
    return ResponseEntity.ok(dataListTopicPage);
  }

  @Operation(
    summary = "Buscar topicos",
    description = "Este endpoint Permite Buscar un Topico.",
    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
      content = @Content(
        mediaType = "application/json",
        schema = @Schema(implementation = TopicoBuscarRequest.class)
      )
    ),
    responses = {
      @ApiResponse(responseCode = "200", description = "Topico Encontrado"),
      @ApiResponse(responseCode = "404", description = "Topico no encontrado")
    }
  )
  @GetMapping("/buscar")
  public ResponseEntity<Page<DatosListarTopico>> searchTopics(
    @RequestBody TopicoBuscarRequest searchRequest,
    @PageableDefault(size = 10, sort = "fechaCreacion") Pageable pageable
  ) {
    Page<DatosListarTopico> dataListTopicPage = service.getTopicsByCourseNameAndDateCreation(
      searchRequest.courseName(),
      searchRequest.dateCreation(),
      pageable
    );
    return ResponseEntity.ok(dataListTopicPage);
  }
  @Operation(
    summary = "Actualizar Topico",
    description = "Este endpoint Permite Modificar la informacion de un Topico",
    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
      content = @Content(
        mediaType = "application/json",
        schema = @Schema(implementation = DatosActualizarTopico.class)
      )
    ),
    responses = {
      @ApiResponse(responseCode = "200", description = "Topico Actualizado successfully"),
      @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos")
    }
  )
  @PutMapping
  @Transactional
  public ResponseEntity<DatosRespuestaTopico> updateTopic(@RequestBody @Valid DatosActualizarTopico dataUpdateTopic) {
    var topic = service.updateTopic(dataUpdateTopic);
    return ResponseEntity.ok(topic);
  }

  @Operation(
    summary = "Eliminar topico",
    description = "Este endpoint Permite Eliminar un Topico"
  )
  @DeleteMapping("/{id}")
  public ResponseEntity deletedTopic(@PathVariable Long id) {
    service.deletedTopic(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(
    summary = "Obtener detalle del topico",
    description = "Este endpoint Permite mostrar  la informacion detallada de un Topico"
  )
  @GetMapping("/{id}")
  @Transactional
  public ResponseEntity<DatosRespuestaTopico> detailsTopic(@PathVariable Long id) {
    DatosRespuestaTopico dataAnswerTopic = service.detailsTopic(id);
    return ResponseEntity.ok(dataAnswerTopic);
  }
}
