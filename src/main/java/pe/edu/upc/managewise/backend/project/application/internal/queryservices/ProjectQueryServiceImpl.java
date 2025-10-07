package pe.edu.upc.managewise.backend.project.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.managewise.backend.project.domain.model.aggregates.Project;
import pe.edu.upc.managewise.backend.project.domain.model.queries.GetAllProjectsQuery;
import pe.edu.upc.managewise.backend.project.domain.model.queries.GetProjectByIdQuery;
import pe.edu.upc.managewise.backend.project.domain.model.queries.GetProjectByNameQuery;
import pe.edu.upc.managewise.backend.project.domain.model.queries.GetProjectsByUserIdQuery;
import pe.edu.upc.managewise.backend.project.domain.services.ProjectQueryService;
import pe.edu.upc.managewise.backend.project.infrastructure.persistence.jpa.repositories.ProjectRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectQueryServiceImpl implements ProjectQueryService {

    private final ProjectRepository projectRepository;

    public ProjectQueryServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public List<Project> handle(GetAllProjectsQuery query) {
        return this.projectRepository.findAll();
    }

    @Override
    public Optional<Project> handle(GetProjectByIdQuery query) {
        return this.projectRepository.findById(query.projectId());
    }

    @Override
    public Optional<Project> handle(GetProjectByNameQuery query) {
        return this.projectRepository.findByName(query.name());
    }

    @Override
    public List<Project> handle(GetProjectsByUserIdQuery query) {
        return this.projectRepository.findByUserId(query.userId());  // Usamos el método creado en el repositorio
    }

}