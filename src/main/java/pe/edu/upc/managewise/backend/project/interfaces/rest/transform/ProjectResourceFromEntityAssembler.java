package pe.edu.upc.managewise.backend.project.interfaces.rest.transform;

import pe.edu.upc.managewise.backend.project.domain.model.aggregates.Project;
import pe.edu.upc.managewise.backend.project.interfaces.rest.resources.ProjectResource;

public class ProjectResourceFromEntityAssembler {
    public static ProjectResource toResourceFromEntity(Project entity) {
        return new ProjectResource(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getUserId(),
                entity.getStatus().toString(), // Convertimos el enum a String
                entity.getStartDate().toString(),
                entity.getEndDate().toString()
        );
    }
}
