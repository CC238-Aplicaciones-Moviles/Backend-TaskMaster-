package pe.edu.upc.managewise.backend.project.interfaces.rest.resources;

public record UpdateProjectResource(Long id, String name, String description, String status, String endDate) {
}