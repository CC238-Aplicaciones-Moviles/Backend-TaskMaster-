package pe.edu.upc.managewise.backend.project.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.managewise.backend.project.domain.model.aggregates.Project;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    boolean existsByName(String name);
    boolean existsByNameAndIdIsNot(String name, Long id);
    Optional<Project> findByName(String name);
    Optional<Project> findById(Long id);
    List<Project> findByUserId(Long userId);
    Optional<Project> findByProjectCode(String projectCode);

}