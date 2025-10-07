package pe.edu.upc.managewise.backend.project.domain.model.commands;

import pe.edu.upc.managewise.backend.project.domain.model.valueobjects.ProjectStatus;
import java.util.Date;

public record UpdateProjectCommand(Long projectId, String name, String description, Date endDate, ProjectStatus status) {
}