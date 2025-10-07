package pe.edu.upc.managewise.backend.project.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.managewise.backend.project.domain.model.aggregates.Project;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    boolean existsByName(String name);  // Verifica si ya existe un proyecto con el mismo nombre
    boolean existsByNameAndIdIsNot(String name, Long id);  // Verifica si ya existe un proyecto con el mismo nombre, pero diferente ID

    Optional<Project> findByName(String name);  // Busca un proyecto por nombre

    Optional<Project> findById(Long id);  // Busca un proyecto por ID (Este es un método de JpaRepository, pero se incluye aquí por claridad)

    List<Project> findByUserId(Long userId);  // Devuelve todos los proyectos asociados con el userId
}