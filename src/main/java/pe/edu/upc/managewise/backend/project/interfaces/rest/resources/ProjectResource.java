package pe.edu.upc.managewise.backend.project.interfaces.rest.resources;

public record ProjectResource(Long id, String name, String description, Long userId, String status, String startDate, String endDate) {
}