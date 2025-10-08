package pe.edu.upc.managewise.backend.project.domain.model.commands;

import pe.edu.upc.managewise.backend.project.domain.model.valueobjects.ProjectStatus;
import java.util.Date;

public record CreateProjectCommand(
        Long userId,
        String name,
        String description,
        Date endDate,
        Double budget
) {
}
