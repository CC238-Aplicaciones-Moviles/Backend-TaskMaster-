package pe.edu.upc.managewise.backend.backlog.domain.model.commands;

import pe.edu.upc.managewise.backend.iam.domain.model.aggregates.User;
import pe.edu.upc.managewise.backend.project.domain.model.aggregates.Project;

import java.util.Date;

/*
public record CreateSprintCommand(Project project, User user, String title, String goal, Date endDate) {
}*/



public record CreateSprintCommand(Project project, String title, String goal, Date endDate) {
}
