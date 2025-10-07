package pe.edu.upc.managewise.backend.backlog.domain.model.commands;

public record CreateEpicCommand(Long userId, String title, String description) {
}
