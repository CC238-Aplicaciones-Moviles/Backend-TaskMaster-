package pe.edu.upc.managewise.backend.backlog.interfaces.rest.transform;

import pe.edu.upc.managewise.backend.backlog.domain.model.commands.CreateSprintCommand;
import pe.edu.upc.managewise.backend.backlog.interfaces.rest.resources.CreateSprintResource;
import pe.edu.upc.managewise.backend.project.domain.model.aggregates.Project;  // Importa el Project

/*
public class CreateSprintCommandFromResourceAssembler {


    public static CreateSprintCommand toCommandFromResource(Project project, Long user, CreateSprintResource resource){
        return new CreateSprintCommand(project, user, resource.title(), resource.goal(), resource.endDate());
    }
}*/

public class CreateSprintCommandFromResourceAssembler {

    // Método actualizado para recibir el Project en lugar de userId
    public static CreateSprintCommand toCommandFromResource(Project project, CreateSprintResource resource){
        return new CreateSprintCommand(project, resource.title(), resource.goal(), resource.endDate());
    }
}

