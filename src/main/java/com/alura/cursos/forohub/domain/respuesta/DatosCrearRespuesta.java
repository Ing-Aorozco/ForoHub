package com.alura.cursos.forohub.domain.respuesta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosCrearRespuesta(
  @NotBlank
  String mensaje,
  @NotNull
  Long usuario_id,
  @NotNull
  Long topico_id,
  Boolean solucionado
) {
}
