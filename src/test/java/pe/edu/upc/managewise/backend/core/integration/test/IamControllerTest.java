package pe.edu.upc.managewise.backend.core.integration.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import pe.edu.upc.managewise.backend.iam.domain.model.aggregates.User;
import pe.edu.upc.managewise.backend.iam.domain.model.entities.Role;
import pe.edu.upc.managewise.backend.iam.domain.model.queries.GetAllUsersQuery;
import pe.edu.upc.managewise.backend.iam.domain.model.queries.GetUserByIdQuery;
import pe.edu.upc.managewise.backend.iam.domain.model.valueobjects.Roles;
import pe.edu.upc.managewise.backend.iam.domain.services.UserQueryService;
import pe.edu.upc.managewise.backend.iam.interfaces.rest.UsersController;
import pe.edu.upc.managewise.backend.iam.interfaces.rest.resources.UserResource;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class IamControllerTest {

    @Mock
    private UserQueryService userQueryService;

    @InjectMocks
    private UsersController usersController;

    @Test
    void testGetAllUsersSuccess() {
        // Arrange
        var user1 = new User("user1", "pass1", List.of(new Role(Roles.ROLE_USER)));
        var user2 = new User("user2", "pass2", List.of(new Role(Roles.ROLE_USER)));
        var users = List.of(user1, user2);

        when(userQueryService.handle(any(GetAllUsersQuery.class))).thenReturn(users);

        // Act
        ResponseEntity<List<UserResource>> response = usersController.getAllUsers();

        // Assert
        assertNotNull(response);
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
        verify(userQueryService, times(1)).handle(any(GetAllUsersQuery.class));
    }


    @Test
    void testGetUserById_NotFound() {
        // Arrange
        Long userId = 99L;
        when(userQueryService.handle(new GetUserByIdQuery(userId))).thenReturn(Optional.empty());

        // Act
        ResponseEntity<UserResource> response = usersController.getUserById(userId);

        // Assert
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        verify(userQueryService, times(1)).handle(new GetUserByIdQuery(userId));
    }



}
