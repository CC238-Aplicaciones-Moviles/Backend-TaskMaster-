package pe.edu.upc.managewise.backend.core.integration.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.upc.managewise.backend.backlog.application.internal.commandservices.UserStoryCommandServiceImpl;
import pe.edu.upc.managewise.backend.backlog.domain.model.aggregates.Epic;
import pe.edu.upc.managewise.backend.backlog.domain.model.aggregates.Sprint;
import pe.edu.upc.managewise.backend.backlog.domain.model.aggregates.UserStory;
import pe.edu.upc.managewise.backend.backlog.domain.model.commands.CreateUserStoryCommand;
import pe.edu.upc.managewise.backend.backlog.infrastructure.persistence.jpa.repositories.EpicRepository;
import pe.edu.upc.managewise.backend.backlog.infrastructure.persistence.jpa.repositories.SprintRepository;
import pe.edu.upc.managewise.backend.backlog.infrastructure.persistence.jpa.repositories.UserStoryRepository;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)

public class BacklogControllerTest {

    @Mock
    private EpicRepository epicRepository;

    @Mock
    private SprintRepository sprintRepository;

    @Mock
    private UserStoryRepository userStoryRepository;

    @InjectMocks
    private UserStoryCommandServiceImpl userStoryCommandServiceImpl;

    @Test
    void testCreateUserStoryLinkedToExistingEpicAndSprint() {
        // Arrange
        Long epicId = 10L;
        Long sprintId = 20L;

        Epic epic = new Epic( 2L, "UX Epic", "Mejoras UX");
        Sprint sprint = new Sprint(1L, "Sprint 1", "Frontend improvements", new Date());

        CreateUserStoryCommand createUserStoryCommand = new CreateUserStoryCommand(
                1L,
                "Crear menú accesible",
                "Como usuario quiero acceder fácilmente al menú principal.",
                epicId,
                sprintId,
                3,
                List.of()
        );

        UserStory savedStory = new UserStory(createUserStoryCommand);

        when(sprintRepository.existsById(sprintId)).thenReturn(true);
        when(epicRepository.existsById(epicId)).thenReturn(true);

        when(userStoryRepository.existsByTitle("Crear menú accesible")).thenReturn(false);
        when(userStoryRepository.save(any(UserStory.class))).thenReturn(savedStory);

        // Act
        Long result = userStoryCommandServiceImpl.handle(createUserStoryCommand);

        // Assert
        assertEquals(10L, createUserStoryCommand.epicId());

    }
}
