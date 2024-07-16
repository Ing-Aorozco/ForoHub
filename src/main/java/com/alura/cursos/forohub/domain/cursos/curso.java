package com.alura.cursos.forohub.domain.cursos;

import com.alura.cursos.forohub.domain.topicos.Topico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Table(name = "cursos")
@Entity(name = "Curso")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class curso {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nombre;

  @Column(name = "categoria")
  @Enumerated(EnumType.STRING)
  private Categoria categoria;

  @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Topico> topicos;

  private Boolean estado;

  public curso(String nombre, Categoria categoria) {
    this.nombre = nombre;
    this.categoria = categoria;
    this.estado = false;
  }

  public void putData(DatosActualizarCurso data) {
    if (data.name() != null) {
      this.nombre = data.name();
    }
    if (data.category() != null) {
      this.categoria = data.category();
    }
  }

  public void deletedCourse() {this.estado = true;}
}
