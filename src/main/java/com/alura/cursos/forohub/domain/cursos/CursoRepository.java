package com.alura.cursos.forohub.domain.cursos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CursoRepository extends JpaRepository<curso, Long> {
  curso findByNombre(String nombre);
  Page<curso> findByEstadoFalse(Pageable pageable);
  Optional<curso> findByNombreIgnoreCase(String nombre);
}
