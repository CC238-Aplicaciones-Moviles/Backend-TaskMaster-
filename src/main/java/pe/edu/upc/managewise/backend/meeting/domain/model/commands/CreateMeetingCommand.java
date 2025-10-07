package pe.edu.upc.managewise.backend.meeting.domain.model.commands;

public record CreateMeetingCommand(Long hostId, String title, String dateStr, String timeStr, String link, String accessCode) {
}
