package pe.edu.upc.managewise.backend.backlog.interfaces.rest.resources;

import pe.edu.upc.managewise.backend.project.domain.model.aggregates.Project;

import java.util.Date;

/*
public record CreateSprintResource(String title, String goal, Date endDate) {

}*/

public record CreateSprintResource(Long projectId, String title, String goal, Date endDate) {
}