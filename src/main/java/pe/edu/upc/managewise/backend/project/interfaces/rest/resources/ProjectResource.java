package pe.edu.upc.managewise.backend.project.interfaces.rest.resources;

import java.util.List;

public record ProjectResource(
        Long id,
        String projectCode,
        String name,
        String description,
        Long userId,
        List<Long> userIds,
        String status,
        String startDate,
        String endDate,
        Double budget
) {
}

