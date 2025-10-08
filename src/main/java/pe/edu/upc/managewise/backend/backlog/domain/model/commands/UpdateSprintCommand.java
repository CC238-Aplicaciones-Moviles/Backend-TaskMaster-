package pe.edu.upc.managewise.backend.backlog.domain.model.commands;

import pe.edu.upc.managewise.backend.backlog.domain.model.valueobjects.SprintStatus;
import pe.edu.upc.managewise.backend.backlog.domain.model.valueobjects.Status;

public record UpdateSprintCommand(Long id, String title, String description, Status status) {
}
