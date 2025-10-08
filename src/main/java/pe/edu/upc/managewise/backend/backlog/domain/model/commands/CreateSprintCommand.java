package pe.edu.upc.managewise.backend.backlog.domain.model.commands;

import pe.edu.upc.managewise.backend.backlog.domain.model.valueobjects.Priority;
import pe.edu.upc.managewise.backend.iam.domain.model.aggregates.User;
import pe.edu.upc.managewise.backend.project.domain.model.aggregates.Project;

import java.util.Date;
import java.util.List;



public record CreateSprintCommand(Project project, String title, String description, Date endDate, List<Long> userIds,
                                  Priority priority) {
}

