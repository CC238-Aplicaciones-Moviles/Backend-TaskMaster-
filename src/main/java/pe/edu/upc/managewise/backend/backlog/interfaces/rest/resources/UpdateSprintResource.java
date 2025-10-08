package pe.edu.upc.managewise.backend.backlog.interfaces.rest.resources;

import pe.edu.upc.managewise.backend.backlog.domain.model.valueobjects.SprintStatus;
import pe.edu.upc.managewise.backend.backlog.domain.model.valueobjects.Status;

public record UpdateSprintResource (
        String title,
        String description,
        Status status

) {
}
