package pe.edu.upc.managewise.backend.core.entities.unit.test;

import org.junit.jupiter.api.Test;
import pe.edu.upc.managewise.backend.iam.domain.model.commands.SignInCommand;
import pe.edu.upc.managewise.backend.iam.domain.model.commands.SignUpCommand;
import pe.edu.upc.managewise.backend.iam.domain.model.entities.Role;
import pe.edu.upc.managewise.backend.iam.domain.model.valueobjects.Roles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IamServiceTest {
    @Test
    void testSignUp() {
        //Arrange
        String username = "Rodolfo";
        String password = "157896";
        List<Role> roles = List.of(
                new Role(
                        Roles.ROLE_USER
                )
        );

        // Act
        SignUpCommand signUpCommand = new SignUpCommand(username, password, roles);

        // Assert
        assertEquals(username, signUpCommand.username());
        assertEquals(password, signUpCommand.password());
        assertEquals(roles, signUpCommand.roles());
    }

    @Test
    void testSignIn() {
        //Arrange
        String username = "María";
        String password = "157896";


        // Act
        SignInCommand signInCommand = new SignInCommand(username, password);

        // Assert
        assertEquals(username, signInCommand.username());
        assertEquals(password, signInCommand.password());
    }
}
