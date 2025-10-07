package pe.edu.upc.managewise.backend.backlog.interfaces.rest.transform;

import pe.edu.upc.managewise.backend.backlog.domain.model.commands.CreateUserStoryCommand;
import pe.edu.upc.managewise.backend.backlog.interfaces.rest.resources.CreateUserStoryResource;

import java.util.Collections;

public class CreateUserStoryCommandFromResourceAssembler {
    public static CreateUserStoryCommand toCommandFromResource(Long userId, CreateUserStoryResource resource){
        return new CreateUserStoryCommand(
                userId,
                resource.title(),
                resource.description(),
                resource.epicId(),
                resource.sprintId(),
                resource.effort(),
                Collections.emptyList());
    }
}
