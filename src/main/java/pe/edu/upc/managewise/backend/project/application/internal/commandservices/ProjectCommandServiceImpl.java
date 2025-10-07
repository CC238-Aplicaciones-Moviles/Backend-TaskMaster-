package pe.edu.upc.managewise.backend.project.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.managewise.backend.project.domain.model.aggregates.Project;
import pe.edu.upc.managewise.backend.project.domain.model.commands.CreateProjectCommand;
import pe.edu.upc.managewise.backend.project.domain.model.commands.DeleteProjectCommand;
import pe.edu.upc.managewise.backend.project.domain.model.commands.UpdateProjectCommand;
import pe.edu.upc.managewise.backend.project.domain.services.ProjectCommandService;
import pe.edu.upc.managewise.backend.project.infrastructure.persistence.jpa.repositories.ProjectRepository;

import java.util.Optional;

@Service
public class ProjectCommandServiceImpl implements ProjectCommandService {

    private final ProjectRepository projectRepository;

    public ProjectCommandServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Long handle(CreateProjectCommand command) {
        var name = command.name();
        if (this.projectRepository.existsByName(name)) {
            throw new IllegalArgumentException("Project with name " + name + " already exists");
        }
        var project = new Project(command);
        try {
            this.projectRepository.save(project);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving project: " + e.getMessage());
        }
        return project.getId();
    }

    @Override
    public Optional<Project> handle(UpdateProjectCommand command) {
        var projectId = command.projectId();
        var name = command.name();
        if (this.projectRepository.existsByNameAndIdIsNot(name, projectId)) {
            throw new IllegalArgumentException("Project with name " + name + " already exists");
        }

        if (!this.projectRepository.existsById(projectId)) {
            throw new IllegalArgumentException("Project with id " + projectId + " does not exist");
        }

        var projectToUpdate = this.projectRepository.findById(projectId).get();
        projectToUpdate.updateInformation(command);

        try {
            var updatedProject = this.projectRepository.save(projectToUpdate);
            return Optional.of(updatedProject);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while updating project: " + e.getMessage());
        }
    }

    @Override
    public void handle(DeleteProjectCommand command) {
        if (!this.projectRepository.existsById(command.projectId())) {
            throw new IllegalArgumentException("Project with id " + command.projectId() + " does not exist");
        }

        try {
            this.projectRepository.deleteById(command.projectId());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while deleting project: " + e.getMessage());
        }
    }
}