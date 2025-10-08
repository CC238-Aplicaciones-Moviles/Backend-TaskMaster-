package pe.edu.upc.managewise.backend.project.interfaces.rest.transform;

import pe.edu.upc.managewise.backend.project.domain.model.aggregates.Project;
import pe.edu.upc.managewise.backend.project.interfaces.rest.resources.ProjectResource;

public class ProjectResourceFromEntityAssembler {
    public static ProjectResource toResourceFromEntity(Project entity) {
        return new ProjectResource(
                entity.getId(),
                entity.getProjectCode().toString(),
                entity.getName(),
                entity.getDescription(),
                entity.getUserId(),
                entity.getUserIds(),
                entity.getStatus().toString(),
                entity.getStartDate().toString(),
                entity.getEndDate().toString(),
                entity.getBudget()
        );
    }
}
