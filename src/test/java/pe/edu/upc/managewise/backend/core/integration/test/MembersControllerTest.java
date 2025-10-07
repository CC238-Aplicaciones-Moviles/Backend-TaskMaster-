package pe.edu.upc.managewise.backend.core.integration.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pe.edu.upc.managewise.backend.members.domain.model.commands.CreateMemberCommand;
import pe.edu.upc.managewise.backend.members.domain.model.queries.GetMemberByIdQuery;
import pe.edu.upc.managewise.backend.members.domain.model.valueobjects.ScrumRoles;
import pe.edu.upc.managewise.backend.members.domain.services.MemberCommandService;
import pe.edu.upc.managewise.backend.members.domain.services.MemberQueryService;
import pe.edu.upc.managewise.backend.members.interfaces.rest.MemberController;
import pe.edu.upc.managewise.backend.members.interfaces.rest.resources.CreateMemberResource;
import pe.edu.upc.managewise.backend.members.interfaces.rest.resources.MemberResource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class MembersControllerTest {

    @Mock
    private MemberQueryService memberQueryService;

    @Mock
    private MemberCommandService memberCommandService;

    @InjectMocks
    private MemberController memberController;

    @Test
    void testCreateMemberFailure() {
        // Arrange
        CreateMemberResource resource = new CreateMemberResource(
                "Pedro Díaz",
                ScrumRoles.SCRUM_MASTER,
                "example@gmail.com",
                "La marina 156"
        );

        when(memberCommandService.handle(any(CreateMemberCommand.class))).thenReturn(0L);

        // Act
        ResponseEntity<MemberResource> response = memberController.createMember(1L,resource);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(memberCommandService).handle(any(CreateMemberCommand.class));
        verify(memberQueryService, never()).handle(any(GetMemberByIdQuery.class));
    }

}
