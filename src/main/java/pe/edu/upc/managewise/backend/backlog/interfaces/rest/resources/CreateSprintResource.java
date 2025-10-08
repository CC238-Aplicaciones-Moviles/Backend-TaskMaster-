package pe.edu.upc.managewise.backend.backlog.interfaces.rest.resources;

import pe.edu.upc.managewise.backend.backlog.domain.model.valueobjects.Priority;
import pe.edu.upc.managewise.backend.project.domain.model.aggregates.Project;

import java.util.Date;
import java.util.List;

/*
public record CreateSprintResource(String title, String goal, Date endDate) {

}*/

public record CreateSprintResource(String title, String description, Date endDate, List<Long> userIds, Priority priority) {
}
