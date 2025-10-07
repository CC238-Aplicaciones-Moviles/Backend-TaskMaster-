package pe.edu.upc.managewise.backend.core.integration.test;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pe.edu.upc.managewise.backend.meeting.domain.model.commands.CreateMeetingCommand;
import pe.edu.upc.managewise.backend.meeting.domain.model.queries.GetMeetingByIdQuery;
import pe.edu.upc.managewise.backend.meeting.domain.services.MeetingCommandService;
import pe.edu.upc.managewise.backend.meeting.domain.services.MeetingQueryService;
import pe.edu.upc.managewise.backend.meeting.interfaces.rest.MeetingController;
import pe.edu.upc.managewise.backend.meeting.interfaces.rest.resources.CreateMeetingResource;
import pe.edu.upc.managewise.backend.meeting.interfaces.rest.resources.MeetingResource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MeetingControllerTest {

    @Mock
    private MeetingQueryService meetingQueryService;

    @Mock
    private MeetingCommandService meetingCommandService;

    @InjectMocks
    private MeetingController meetingController;


    @Test
    void testCreateMeetingFailure() {
        // Arrange
        CreateMeetingResource resource = new CreateMeetingResource(
                "Reunión fallida",
                "2025-05-15",
                "10:00",
                "https://meet.example.com",
                "asd"
        );

        when(meetingCommandService.handle(any(CreateMeetingCommand.class)))
                .thenReturn(0L);

        // Act
        ResponseEntity<MeetingResource> response = meetingController.createMeeting(1L, resource);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(meetingCommandService).handle(any(CreateMeetingCommand.class));
        verify(meetingQueryService, never()).handle(any(GetMeetingByIdQuery.class));
    }
}
