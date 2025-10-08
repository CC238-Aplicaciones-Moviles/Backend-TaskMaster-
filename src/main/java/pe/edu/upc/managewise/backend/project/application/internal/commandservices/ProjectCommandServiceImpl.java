package pe.edu.upc.managewise.backend.project.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.managewise.backend.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import pe.edu.upc.managewise.backend.project.domain.model.aggregates.Project;
import pe.edu.upc.managewise.backend.project.domain.model.commands.*;
import pe.edu.upc.managewise.backend.project.domain.services.ProjectCommandService;
import pe.edu.upc.managewise.backend.project.infrastructure.persistence.jpa.repositories.ProjectRepository;
import java.util.Optional;
@Service
public class ProjectCommandServiceImpl implements ProjectCommandService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectCommandServiceImpl(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
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
        var projectToUpdate = this.projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));
        projectToUpdate.updateInformation(command);

        try {
            return Optional.of(this.projectRepository.save(projectToUpdate));
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while updating project: " + e.getMessage());
        }
    }

    @Override
    public void handle(DeleteProjectCommand command) {
        if (!this.projectRepository.existsById(command.projectId())) {
            throw new IllegalArgumentException("Project not found");
        }
        try {
            this.projectRepository.deleteById(command.projectId());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while deleting project: " + e.getMessage());
        }
    }

    @Override
    public void handleAddUserToProject(AddUserToProjectCommand command) {
        boolean userExists = userRepository.existsById(command.userId());
        if (!userExists) {
            throw new IllegalArgumentException("User with ID " + command.userId() + " does not exist");
        }
        var project = projectRepository.findById(command.projectId())
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));

        if (!project.getUserIds().contains(command.userId())) {
            project.getUserIds().add(command.userId());
            try {
                projectRepository.save(project);
            } catch (Exception e) {
                throw new IllegalArgumentException("Error while adding user to project: " + e.getMessage());
            }
        } else {
            throw new IllegalArgumentException("User is already part of the project");
        }
    }

    @Override
    public void handleRemoveUserFromProject(RemoveUserFromProjectCommand command) {
        boolean userExists = userRepository.existsById(command.userId());
        if (!userExists) {
            throw new IllegalArgumentException("User with ID " + command.userId() + " does not exist");
        }

        var project = projectRepository.findById(command.projectId())
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));

        if (project.getUserIds().contains(command.userId())) {
            project.getUserIds().remove(command.userId());
            try {
                projectRepository.save(project);
            } catch (Exception e) {
                throw new IllegalArgumentException("Error while removing user from project: " + e.getMessage());
            }
        } else {
            throw new IllegalArgumentException("User is not part of the project");
        }
    }
}
