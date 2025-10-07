package pe.edu.upc.managewise.backend.core.integration.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pe.edu.upc.managewise.backend.issues.domain.model.aggregates.Issue;
import pe.edu.upc.managewise.backend.issues.domain.model.commands.CreateIssueCommand;
import pe.edu.upc.managewise.backend.issues.domain.model.queries.GetIssueByIdQuery;
import pe.edu.upc.managewise.backend.issues.domain.model.valueobjects.IssuePriorities;
import pe.edu.upc.managewise.backend.issues.domain.model.valueobjects.IssueStatuses;
import pe.edu.upc.managewise.backend.issues.domain.services.IssueCommandService;
import pe.edu.upc.managewise.backend.issues.domain.services.IssueQueryService;
import pe.edu.upc.managewise.backend.issues.interfaces.rest.IssuesController;
import pe.edu.upc.managewise.backend.issues.interfaces.rest.resources.CreateIssueResource;
import pe.edu.upc.managewise.backend.issues.interfaces.rest.resources.IssueResource;
import pe.edu.upc.managewise.backend.issues.interfaces.rest.transform.CreateIssueCommandFromResourceAssembler;
import pe.edu.upc.managewise.backend.issues.interfaces.rest.transform.IssueResourceFromEntityAssembler;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class IssuesControllerTest {

    @Mock
    private IssueQueryService issueQueryService;

    @Mock
    private IssueCommandService issueCommandService;

    @InjectMocks
    private IssuesController issuesController;

    @Test
    void testCreateIssueSuccess() {
        // Arrange
        CreateIssueResource resource = new CreateIssueResource(
                "Error en formulario",
                "Sprint 15",
                "El formulario no guarda",
                IssueStatuses.TO_DO,
                IssuePriorities.HIGH,
                "juan.perez",
                "ana.gomez",
                "2025-05-07",
                "2025-05-13"
        );

        Long expectedId = 1L;
        CreateIssueCommand expectedCommand = CreateIssueCommandFromResourceAssembler.toCommandFromResource(1L, resource);

        when(issueCommandService.handle(any(CreateIssueCommand.class))).thenReturn(expectedId);

        Issue expectedIssue = new Issue(expectedCommand);
        when(issueQueryService.handle(new GetIssueByIdQuery(expectedId))).thenReturn(Optional.of(expectedIssue));

        IssueResource expectedResource = IssueResourceFromEntityAssembler.toResourceFromEntity(expectedIssue);

        // Act
        ResponseEntity<IssueResource> response = issuesController.createIssue(1L,resource);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedResource, response.getBody());
        verify(issueCommandService, times(1)).handle(expectedCommand);
        verify(issueQueryService, times(1)).handle(new GetIssueByIdQuery(expectedId));
    }


    @Test
    void testCreateIssueFailure() {
        // Arrange
        CreateIssueResource resource = new CreateIssueResource(
                "Error en formulario",
                "Sprint 15",
                "El formulario no guarda",
                IssueStatuses.TO_DO,
                IssuePriorities.HIGH,
                "juan.perez",
                "ana.gomez",
                "2025-05-07",
                "2025-05-13"
        );

        when(issueCommandService.handle(any(CreateIssueCommand.class))).thenReturn(0L);

        // Act
        ResponseEntity<IssueResource> response = issuesController.createIssue(1L, resource);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(issueCommandService, times(1)).handle(any(CreateIssueCommand.class));
        verify(issueQueryService, never()).handle(any(GetIssueByIdQuery.class));
    }
}
