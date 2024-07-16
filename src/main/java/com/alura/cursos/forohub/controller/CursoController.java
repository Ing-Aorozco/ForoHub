package com.alura.cursos.forohub.controller;


import com.alura.cursos.forohub.domain.cursos.*;
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
@RequestMapping("/curso")
@SecurityRequirement(name = "bearer-key")
public class CursoController {

  @Autowired
  private CursoService service;

  @Operation(
    summary = "Crear Curso",
    description = "Este endpoint Permite Crear un Curso.",
    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
      content = @Content(
        mediaType = "application/json",
        schema = @Schema(implementation = DatosCrearCurso.class)
      )
    ),
    responses = {
      @ApiResponse(responseCode = "201", description = "Curso Creado successfully"),
      @ApiResponse(responseCode = "400", description = "Datos de Entrada Invalidos")
    }
  )
  @PostMapping
  @Transactional
  public ResponseEntity<DatosRespuestaCurso> createCourse(
    @RequestBody @Valid DatosCrearCurso dataCreateCourse,
    UriComponentsBuilder uriComponentsBuilder
  ) {
    DatosRespuestaCurso response = service.createCourse(dataCreateCourse);
    URI url = uriComponentsBuilder.path("/curso/{id}").buildAndExpand(response.id()).toUri();

    return ResponseEntity.created(url).body(response);
  }

  @Operation(
    summary = "Listar cursos",
    description = "Este endpoint Permite listar los curso"
  )
  @GetMapping
  public ResponseEntity<Page<DatosListarCurso>> listCourse(
    @Parameter(description = "Numero de Paginas")
    @PageableDefault(size = 10, sort = "nombre")
    Pageable pageable
  ) {
    Page<DatosListarCurso> dataListCourses = service.getCourse(pageable);
    return ResponseEntity.ok(dataListCourses);
  }

  @Operation(
    summary = "Buscar Curso",
    description = "Este endpoint Permite Buscar un curso por Nombre",
    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
      content = @Content(
        mediaType = "application/json",
        schema = @Schema(implementation = CursoBuscarReques.class)
      )
    ),
    responses = {
      @ApiResponse(responseCode = "200", description = "Curso Encotrado"),
      @ApiResponse(responseCode = "404", description = "Curso no Encontrado")
    }
  )
  @GetMapping("/buscar")
  public ResponseEntity<DatosListarCurso> searchCourse(@RequestBody @Valid CursoBuscarReques courseSearchReques) {
    DatosListarCurso dataListCourse = service.getCourseByName(courseSearchReques.name());
    return ResponseEntity.ok(dataListCourse);
  }

  @Operation(
    summary = "Actualizar Curso",
    description = "Este endpoint Permite Actualizar la informacion de un Curso.",
    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
      content = @Content(
        mediaType = "application/json",
        schema = @Schema(implementation = DatosActualizarCurso.class)
      )
    ),
    responses = {
      @ApiResponse(responseCode = "200", description = "Curso actualizado successfully"),
      @ApiResponse(responseCode = "400", description = "Datos de entrada Invalidos")
    }
  )
  @PutMapping
  @Transactional
  public ResponseEntity<DatosRespuestaCurso> updateCourse(@RequestBody @Valid DatosActualizarCurso dataUpdateCourse) {
    var course = service.updateCourse(dataUpdateCourse);
    return ResponseEntity.ok(course);
  }

  @Operation(
    summary = "Eliminar Curso",
    description = "Este endpoint Permite Eliminar un Curso"
  )
  @DeleteMapping("/{id}")
  public ResponseEntity deletedCourse(@PathVariable Long id) {
    service.deletedCourse(id);
    return ResponseEntity.noContent().build();
  }
}
