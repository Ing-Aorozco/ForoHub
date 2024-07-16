package com.alura.cursos.forohub.domain.topicos;

import com.alura.cursos.forohub.domain.cursos.curso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
  Optional<Topico> findByTitulo(String title);
  Optional<Topico> findByMensaje(String message);
  Page<Topico> findByEstadoFalse(Pageable pageable);
  Page<Topico> findByCursoAndFechaCreacionAfter(curso course, LocalDateTime dateCreation, Pageable pageable);
}

