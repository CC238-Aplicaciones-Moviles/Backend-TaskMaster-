package pe.edu.upc.managewise.backend.backlog.interfaces.rest.transform;

import pe.edu.upc.managewise.backend.backlog.domain.model.aggregates.Sprint;
import pe.edu.upc.managewise.backend.backlog.interfaces.rest.resources.SprintResource;

import java.util.List;

public class SprintResourceFromEntityAssembler {
    public static SprintResource toResourceFromEntity(Sprint sprint) {
        List<Long> userIds = sprint.getMembers();
        Long projectId = sprint.getProject().getId();
        return new SprintResource(
                sprint.getId(),
                projectId,
                sprint.getTitle(),
                sprint.getDescription(),
                sprint.getEndDate(),
                sprint.getStartDate(),
                sprint.getStatus(),
                userIds,
                sprint.getPriority()
        );
    }
}

