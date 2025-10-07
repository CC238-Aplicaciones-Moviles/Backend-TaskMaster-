package pe.edu.upc.managewise.backend.core.entities.unit.test;

import org.junit.jupiter.api.Test;
import pe.edu.upc.managewise.backend.members.domain.model.commands.CreateMemberCommand;
import pe.edu.upc.managewise.backend.members.domain.model.commands.DeleteMemberCommand;
import pe.edu.upc.managewise.backend.members.domain.model.commands.UpdateMemberCommand;
import pe.edu.upc.managewise.backend.members.domain.model.valueobjects.ScrumRoles;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MembersServiceTest {

    @Test
    void testCreateMember() {

        // Arrange
        Long userId = 1L;
        String fullName = "Pedro Díaz";
        String email = "example@gmail.com";
        String streetAddress = "La marina 156";
        ScrumRoles role = ScrumRoles.DEVELOPMENT_TEAM;

        // Act
        CreateMemberCommand createMemberCommand = new CreateMemberCommand(userId, fullName, email, streetAddress, role);

        // Assert
        assertEquals(userId, createMemberCommand.userId());
        assertEquals(fullName, createMemberCommand.fullName());
        assertEquals(email, createMemberCommand.email());
        assertEquals(streetAddress, createMemberCommand.streetAddress());
        assertEquals(role, createMemberCommand.role());
    }

    @Test
    void testUpdateMember() {

        // Arrange
        Long memberId = 1L;
        String fullName = "Pedro Perez";
        String email = "pedropez@gmail.com";
        String streetAddress = "La marina 156";
        ScrumRoles role = ScrumRoles.DEVELOPMENT_TEAM;

        // Act
        UpdateMemberCommand updateMemberCommand = new UpdateMemberCommand( memberId, fullName, email, streetAddress, role);

        // Assert
        assertEquals(memberId, updateMemberCommand.memberId());
        assertEquals(fullName, updateMemberCommand.fullName());
        assertEquals(email, updateMemberCommand.email());
        assertEquals(streetAddress, updateMemberCommand.streetAddress());
        assertEquals(role, updateMemberCommand.role());
    }

    @Test
    void testDeleteMember() {

        // Arrange
        Long memberId = 1L;

        // Act
        DeleteMemberCommand deleteMemberCommand = new DeleteMemberCommand(memberId);

        // Assert
        assertEquals(memberId, deleteMemberCommand.memberId());
    }
}
