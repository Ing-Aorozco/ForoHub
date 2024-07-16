package com.alura.cursos.forohub.domain.topicos;

import com.alura.cursos.forohub.domain.respuesta.Respuesta;
import com.alura.cursos.forohub.domain.cursos.curso;
import com.alura.cursos.forohub.domain.usuarios.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String titulo;

  private String mensaje;

  private LocalDateTime fechaCreacion;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private TopicoStatus status;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "usuario_id")
  private Usuario usuario;

  @OneToMany(mappedBy = "topicos", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<Respuesta> respuesta = new ArrayList<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "curso_id")
  private curso curso;

  private Boolean estado;
  public Topico(
    String titulo,
    String mensaje,
    Usuario usuario,
    curso curso)
  {
    this.titulo = titulo;
    this.mensaje = mensaje;
    this.fechaCreacion = LocalDateTime.now();
    this.status = TopicoStatus.UNANSWERED;
    this.usuario = usuario;
    this.curso = curso;
    this.estado = false;
  }

  public void setStatus(TopicoStatus status) {
    this.status = status;
  }

  public void putData(DatosActualizarTopico data) {
    if (data.title() != null) {
      this.titulo = data.title();
    }
    if (data.message() != null) {
      this.mensaje = data.message();
    }
  }

  public void deletedTopic() {this.estado = true;}
}
