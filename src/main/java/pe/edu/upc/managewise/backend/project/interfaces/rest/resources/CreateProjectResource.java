package pe.edu.upc.managewise.backend.project.interfaces.rest.resources;

public record CreateProjectResource(String name, String description, Long userId, String status, String endDate) {
}