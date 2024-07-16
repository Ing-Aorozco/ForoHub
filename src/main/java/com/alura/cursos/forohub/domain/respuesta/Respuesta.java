package com.alura.cursos.forohub.domain.respuesta;

import com.alura.cursos.forohub.domain.topicos.Topico;
import com.alura.cursos.forohub.domain.usuarios.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Table(name = "respuesta")
@Entity(name = "Respuesta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Respuesta {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String mensaje;

  private Boolean solucionado;

  private LocalDateTime fechaCreacion;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "topico_id")
  private Topico topicos;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "usuario_id")
  private Usuario usuario;

  private Boolean estado;

  public Respuesta(String message, Boolean solved, Topico topicos, Usuario usuario) {
    this.mensaje = message;
    this.solucionado = solved != null ? solved : false;
    this.fechaCreacion = LocalDateTime.now();
    this.topicos = topicos;
    this.usuario = usuario;
    this.estado = false;
  }

  public void putData(DatosActualizarRespuesta data) {
    if (data.message() != null) {
      this.mensaje = data.message();
      this.fechaCreacion = LocalDateTime.now();
    }
  }

  public void deletedAnswer() {this.estado = true;}
}
