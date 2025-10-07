package pe.edu.upc.managewise.backend.project.domain.services;

import pe.edu.upc.managewise.backend.project.domain.model.aggregates.Project;
import pe.edu.upc.managewise.backend.project.domain.model.commands.CreateProjectCommand;
import pe.edu.upc.managewise.backend.project.domain.model.commands.DeleteProjectCommand;
import pe.edu.upc.managewise.backend.project.domain.model.commands.UpdateProjectCommand;

import java.util.Optional;

public interface ProjectCommandService {
    Long handle(CreateProjectCommand command);  // Crea un nuevo proyecto y devuelve el ID
    Optional<Project> handle(UpdateProjectCommand command);  // Actualiza un proyecto y devuelve el proyecto actualizado
    void handle(DeleteProjectCommand command);  // Elimina un proyecto
}
