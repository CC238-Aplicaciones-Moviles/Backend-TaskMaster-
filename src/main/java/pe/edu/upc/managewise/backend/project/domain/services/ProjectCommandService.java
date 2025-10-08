package pe.edu.upc.managewise.backend.project.domain.services;

import pe.edu.upc.managewise.backend.project.domain.model.aggregates.Project;
import pe.edu.upc.managewise.backend.project.domain.model.commands.*;

import java.util.Optional;

public interface ProjectCommandService {
    Long handle(CreateProjectCommand command);
    Optional<Project> handle(UpdateProjectCommand command);
    void handle(DeleteProjectCommand command);
    void handleAddUserToProject(AddUserToProjectCommand command);
    void handleRemoveUserFromProject(RemoveUserFromProjectCommand command);
}
