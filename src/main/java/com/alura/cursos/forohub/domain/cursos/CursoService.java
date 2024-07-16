package com.alura.cursos.forohub.domain.cursos;

import com.alura.cursos.forohub.infra.errors.IntegrityValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CursoService {

  @Autowired
  private CursoRepository courseRepository;

  public DatosRespuestaCurso createCourse(DatosCrearCurso dataCreateCourse) {
    Optional<curso> existingCourse = Optional.ofNullable(courseRepository.findByNombre(dataCreateCourse.nombre()));

    if (existingCourse.isPresent()) {
      throw new IntegrityValidation("Curso ya existe");
    }

    curso newCourse = new curso(dataCreateCourse.nombre(), dataCreateCourse.categoria());
    courseRepository.save(newCourse);
    return new DatosRespuestaCurso(newCourse);
  }

  public Page<DatosListarCurso> getCourse(Pageable pageable) {
    Page<curso> coursePage = courseRepository.findByEstadoFalse(pageable);
    return coursePage.map(DatosListarCurso::new);
  }

  public DatosListarCurso getCourseByName(String courseName) {
    Optional<curso> existingCourse = courseRepository.findByNombreIgnoreCase(courseName);

    if (existingCourse.isEmpty()) {
      throw new IntegrityValidation("Course does not exist");
    }

    return new DatosListarCurso(existingCourse.get());
  }

  public DatosRespuestaCurso updateCourse(DatosActualizarCurso dataUpdateCourse) {
    if (!courseRepository.findById(dataUpdateCourse.id()).isPresent()) {
      throw new IntegrityValidation("id course not found");
    }

    curso course = courseRepository.getReferenceById(dataUpdateCourse.id());
    course.putData(dataUpdateCourse);

    return new DatosRespuestaCurso(course);
  }

  public void deletedCourse(Long id) {
    curso course = courseRepository.findById(id)
      .orElseThrow(() -> new IntegrityValidation("Course not found with id"));

    course.deletedCourse();
    courseRepository.save(course);
  }
}
