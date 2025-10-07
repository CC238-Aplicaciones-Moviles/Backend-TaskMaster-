package pe.edu.upc.managewise.backend.core.entities.unit.test;

import org.junit.jupiter.api.Test;
import pe.edu.upc.managewise.backend.meeting.domain.model.commands.CreateMeetingCommand;
import pe.edu.upc.managewise.backend.meeting.domain.model.commands.UpdateMeetingCommand;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MeetingServiceTest {
    @Test
    void testCreateMeeting() {

        //Arrange
        Long hostId = 1L;
        String title = "Sprint 1";
        String dateStr = "2025-05-01";
        String timeStr = "45 min";
        String link = "url";
        String accessCode = "ard";


        // Act
        CreateMeetingCommand createMeetingCommand = new CreateMeetingCommand(hostId, title, dateStr, timeStr, link, accessCode);

        // Assert
        assertEquals(hostId, createMeetingCommand.hostId());
        assertEquals(title, createMeetingCommand.title());
        assertEquals(dateStr, createMeetingCommand.dateStr());
        assertEquals(timeStr, createMeetingCommand.timeStr());
        assertEquals(link, createMeetingCommand.link());
        assertEquals(accessCode, createMeetingCommand.accessCode());
    }


    @Test
    void testUpdateMeeting() {

        //Arrange
        Long meetingId = 1L;
        String title = "Sprint 1";
        String dateStr = "2025-08-01";
        String timeStr = "45 min";
        String link = "urlCorregida";
        String accessCode = "ads";


        // Act
        UpdateMeetingCommand updateMeetingCommand = new UpdateMeetingCommand(meetingId, title, dateStr, timeStr, link, accessCode);

        // Assert
        assertEquals(meetingId, updateMeetingCommand.meetingId());
        assertEquals(title, updateMeetingCommand.title());
        assertEquals(dateStr, updateMeetingCommand.dateStr());
        assertEquals(timeStr, updateMeetingCommand.timeStr());
        assertEquals(link, updateMeetingCommand.link());
        assertEquals(accessCode, updateMeetingCommand.accessCode());
    }

}
