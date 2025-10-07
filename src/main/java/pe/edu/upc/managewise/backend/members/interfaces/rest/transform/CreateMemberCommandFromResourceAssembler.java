package pe.edu.upc.managewise.backend.members.interfaces.rest.transform;

import pe.edu.upc.managewise.backend.members.domain.model.commands.CreateMemberCommand;
import pe.edu.upc.managewise.backend.members.interfaces.rest.resources.CreateMemberResource;

public class CreateMemberCommandFromResourceAssembler {
    public static CreateMemberCommand toCommandFromResource(Long userId, CreateMemberResource resource) {
        return new CreateMemberCommand(
                userId,
                resource.fullName(),
                resource.email(),
                resource.streetAddress(),
                resource.role()
        );
    }
}
