package pe.edu.upc.managewise.backend.core.entities.unit.test;

import org.junit.jupiter.api.Test;
import pe.edu.upc.managewise.backend.issues.domain.model.commands.CreateEventByIssueIdCommand;
import pe.edu.upc.managewise.backend.issues.domain.model.commands.CreateIssueCommand;
import pe.edu.upc.managewise.backend.issues.domain.model.commands.DeleteIssueCommand;
import pe.edu.upc.managewise.backend.issues.domain.model.valueobjects.IssuePriorities;
import pe.edu.upc.managewise.backend.issues.domain.model.valueobjects.IssueStatuses;
import pe.edu.upc.managewise.backend.issues.interfaces.rest.resources.EventRecordItemResource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IssuesServiceTest {

    @Test
    void testCreateIssue() {

        // Arrange
        Long userId = 1L;
        String title = "Error al guardar formulario";
        String sprintAssociate = "Sprint 15";
        String description = "Al hacer clic en guardar, el formulario no se persiste correctamente.";
        IssueStatuses status = IssueStatuses.TO_DO;
        IssuePriorities priority = IssuePriorities.HIGH;
        String assignedTo = "juan.perez";
        String madeBy = "ana.gomez";
        String createdIn = "2025-05-07";
        String resolutionDate = "2025-05-13";

        List<EventRecordItemResource> history = List.of(
                new EventRecordItemResource(1L, "2025-05-07", "ana.gomez", "Formulario", "Al hacer clic en guardar, el formulario no se persiste correctamente."),
                new EventRecordItemResource(2L, "2025-05-13", "juan.perez", "Formulario", "Al hacer clic en guardar, el formulario no se persiste correctamente.")
        );

        // Act
        CreateIssueCommand createIssueCommand = new CreateIssueCommand(
                userId,
                title,
                sprintAssociate,
                description,
                status,
                priority,
                assignedTo,
                madeBy,
                createdIn,
                resolutionDate,
                history
        );

        // Assert
        assertEquals(userId, createIssueCommand.userId());
        assertEquals(title, createIssueCommand.title());
        assertEquals(sprintAssociate, createIssueCommand.sprintAssociate());
        assertEquals(description, createIssueCommand.description());
        assertEquals(status, createIssueCommand.status());
        assertEquals(priority, createIssueCommand.priority());
        assertEquals(assignedTo, createIssueCommand.assignedTo());
        assertEquals(madeBy, createIssueCommand.madeBy());
        assertEquals(createdIn, createIssueCommand.createdIn());
        assertEquals(resolutionDate, createIssueCommand.resolutionDate());
        assertEquals(history, createIssueCommand.history());
    }


    @Test
    void testDeleteIssue() {

        // Arrange
        Long issueId = 1L;

        // Act
        DeleteIssueCommand deleteIssueCommand = new DeleteIssueCommand(issueId);

        // Assert
        assertEquals(issueId, deleteIssueCommand.issueId());
    }

    @Test
    void testCreateEventByIssueId() {

        // Arrange
        Long issueId = 2L;
        String createdDate = "2025-05-07";
        String madeBy = "ana.gomez";
        String eventName = "Formulario";
        String description = "Al hacer clic en guardar, el formulario no se persiste correctamente.";

        // Act
        CreateEventByIssueIdCommand createEventByIssueIdCommand = new CreateEventByIssueIdCommand(
                issueId, createdDate,
                madeBy, eventName, description
        );

        // Assert
        assertEquals(issueId, createEventByIssueIdCommand.issueId());
        assertEquals(createdDate, createEventByIssueIdCommand.createdDate());
        assertEquals(madeBy, createEventByIssueIdCommand.madeBy());
        assertEquals(eventName, createEventByIssueIdCommand.eventName());
        assertEquals(description, createEventByIssueIdCommand.description());
    }
}
