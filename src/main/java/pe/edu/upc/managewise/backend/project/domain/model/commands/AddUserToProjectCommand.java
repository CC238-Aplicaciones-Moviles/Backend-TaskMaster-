package pe.edu.upc.managewise.backend.project.domain.model.commands;

public record AddUserToProjectCommand(Long projectId, Long userId) {
}
