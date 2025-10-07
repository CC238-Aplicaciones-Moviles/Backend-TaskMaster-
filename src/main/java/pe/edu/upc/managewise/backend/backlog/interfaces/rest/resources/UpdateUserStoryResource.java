package pe.edu.upc.managewise.backend.backlog.interfaces.rest.resources;

import pe.edu.upc.managewise.backend.backlog.domain.model.valueobjects.Status;

import java.util.List;

public record UpdateUserStoryResource(
        String title,
        String description,
        Long epicId,
        Long sprintId,
        Status status,
        Integer effort
) {
}
