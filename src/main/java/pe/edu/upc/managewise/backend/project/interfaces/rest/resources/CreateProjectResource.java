package pe.edu.upc.managewise.backend.project.interfaces.rest.resources;

import java.util.List;

public record CreateProjectResource(
        String name,
        String description,
        Double budget,
        String endDate
) {
}
