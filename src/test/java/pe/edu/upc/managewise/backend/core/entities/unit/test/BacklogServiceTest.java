package pe.edu.upc.managewise.backend.core.entities.unit.test;

import org.junit.jupiter.api.Test;
import pe.edu.upc.managewise.backend.backlog.domain.model.commands.CreateEpicCommand;
import pe.edu.upc.managewise.backend.backlog.domain.model.commands.CreateSprintCommand;
import pe.edu.upc.managewise.backend.backlog.domain.model.commands.CreateUserStoryCommand;
import pe.edu.upc.managewise.backend.backlog.domain.model.valueobjects.Status;
import pe.edu.upc.managewise.backend.backlog.interfaces.rest.resources.TaskItemResource;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BacklogServiceTest {
    @Test
    void testCreateEpic() {

        //Arrange
        String title = "Experiencia del Usuario en la Landing Page";
        String description = "Esta epic mapeará toda la experiencia del usuario que pasa durante el uso de la landing page.";


        // Act
        CreateEpicCommand createEpicCommand = new CreateEpicCommand(2L, title, description);

        // Assert
        assertEquals(title, createEpicCommand.title());
        assertEquals(description, createEpicCommand.description());

    }

    @Test
    void testCreateSprint() {

        // Arrange
        Long userId = 1L;
        String title = "Sprint 1: Front End";
        String goal = "Diseñar y desplegar un frontend pertinente para la solución planteada.";
        Date endDate = new Date();


        // Act
        CreateSprintCommand createSprintCommand = new CreateSprintCommand(userId, title, goal, endDate);

        // Assert
        assertEquals(userId, createSprintCommand.userId());
        assertEquals(title, createSprintCommand.title());
        assertEquals(goal, createSprintCommand.goal());
        assertEquals(endDate, createSprintCommand.endDate());
    }

    @Test
    void testCreateUserStory() {

        // Arrange
        Long userId = 1L;
        String title = "Crear una nueva incidencia";
        String description = "Como miembro del equipo, quiero crear una nueva incidencia, para reportar un problema o sugerir una mejora en el sistema.";
        Long epicId = 154L;
        Long sprintId = 25L;
        Integer effort = 3;
        List<TaskItemResource> tasks = List.of(
                new TaskItemResource(
                        1L,
                        "Investigar causa raíz",
                        "Recolectar logs y datos para determinar la causa.",
                        Status.DONE,
                        2
                ),
                new TaskItemResource(
                        2L,
                        "Implementar parche",
                        "Escribir y desplegar el fix provisional.",
                        Status.IN_PROGRESS,
                        5
                )
        );


        // Act
        CreateUserStoryCommand createUserStoryCommand = new CreateUserStoryCommand(userId, title, description, epicId, sprintId, effort, tasks);

        // Assert
        assertEquals(userId, createUserStoryCommand.userId());
        assertEquals(title, createUserStoryCommand.title());
        assertEquals(description, createUserStoryCommand.description());
        assertEquals(epicId, createUserStoryCommand.epicId());
        assertEquals(sprintId, createUserStoryCommand.sprintId());
        assertEquals(effort, createUserStoryCommand.effort());
        assertEquals(tasks, createUserStoryCommand.tasks());
    }
}
