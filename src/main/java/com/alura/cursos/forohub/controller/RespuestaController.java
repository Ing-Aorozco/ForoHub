package com.alura.cursos.forohub.controller;

import com.alura.cursos.forohub.domain.respuesta.*;

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
@SecurityRequirement(name = "bearer-key")
@RequestMapping("/answer")
public class RespuestaController {

  @Autowired
  private RespuestaService service;

  @Operation(
    summary = "Create a new answer",
    description = "This endpoint allows users to create a new answer in the forum.",
    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
      content = @Content(
        mediaType = "application/json",
        schema = @Schema(implementation = DatosCrearRespuesta.class)
      )
    ),
    responses = {
      @ApiResponse(responseCode = "201", description = "Answer created successfully"),
      @ApiResponse(responseCode = "400", description = "Invalid input data")
    }
  )
  @PostMapping
  @Transactional
  public ResponseEntity<DatosRespuesta> createAnswer(
    @RequestBody @Valid DatosCrearRespuesta dataCreateAnswer,
    UriComponentsBuilder uriComponentsBuilder
  ) {
    DatosRespuesta response = service.createAnswer(dataCreateAnswer);
    URI url = uriComponentsBuilder.path("/answer/{id}").buildAndExpand(response.id()).toUri();

    return ResponseEntity.created(url).body(response);
  }

  @Operation(
    summary = "List answers",
    description = "This endpoint allows users to list all answers in the forum."
  )
  @GetMapping
  public ResponseEntity<Page<DatosListarRespuesta>> listAnswer(
    @Parameter(description = "The page number to retrieve")
    @PageableDefault(size = 10, sort = "dateCreation")
    Pageable pageable
  ) {
    Page<DatosListarRespuesta> dataListAnswers = service.getAnswer(pageable);
    return ResponseEntity.ok(dataListAnswers);
  }

  @Operation(
    summary = "Update an answer",
    description = "This endpoint allows users to update an existing answer.",
    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
      content = @Content(
        mediaType = "application/json",
        schema = @Schema(implementation = DatosActualizarRespuesta.class)
      )
    ),
    responses = {
      @ApiResponse(responseCode = "200", description = "Answer updated successfully"),
      @ApiResponse(responseCode = "400", description = "Invalid input data")
    }
  )
  @PutMapping
  @Transactional
  public ResponseEntity<DatosRespuesta> updateAnswer(@RequestBody @Valid DatosActualizarRespuesta dataUpdateAnswer) {
    var answer = service.updateAnswer(dataUpdateAnswer);
    return ResponseEntity.ok(answer);
  }

  @Operation(
    summary = "Delete an answer",
    description = "This endpoint allows users to delete an existing answer."
  )
  @DeleteMapping("/{id}")
  public ResponseEntity deletedAnswer(@PathVariable Long id) {
    service.deletedAnswer(id);
    return ResponseEntity.noContent().build();
  }
}