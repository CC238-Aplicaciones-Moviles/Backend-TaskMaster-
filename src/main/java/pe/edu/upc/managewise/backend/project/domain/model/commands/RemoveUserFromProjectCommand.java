package pe.edu.upc.managewise.backend.project.domain.model.commands;

public record RemoveUserFromProjectCommand(Long projectId, Long userId) {
}