package pe.edu.upc.managewise.backend.backlog.interfaces.rest.resources;

import pe.edu.upc.managewise.backend.backlog.domain.model.valueobjects.Priority;
import pe.edu.upc.managewise.backend.backlog.domain.model.valueobjects.Status;

import java.util.Date;
import java.util.List;

public record SprintResource(
        Long id,
        Long projectId,
        String title,
        String description,
        Date endDate,
        Date startDate,
        Status status,
        List<Long> userIds,
        Priority priority

) {}
