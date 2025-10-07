package pe.edu.upc.managewise.backend.project.domain.services;

import pe.edu.upc.managewise.backend.project.domain.model.aggregates.Project;
import pe.edu.upc.managewise.backend.project.domain.model.queries.GetAllProjectsQuery;
import pe.edu.upc.managewise.backend.project.domain.model.queries.GetProjectByIdQuery;
import pe.edu.upc.managewise.backend.project.domain.model.queries.GetProjectByNameQuery;
import pe.edu.upc.managewise.backend.project.domain.model.queries.GetProjectsByUserIdQuery;

import java.util.List;
import java.util.Optional;

public interface ProjectQueryService {
    List<Project> handle(GetAllProjectsQuery query);  // Devuelve todos los proyectos
    Optional<Project> handle(GetProjectByIdQuery query);  // Devuelve un proyecto por ID
    Optional<Project> handle(GetProjectByNameQuery query);  // Devuelve un proyecto por nombre
    List<Project> handle(GetProjectsByUserIdQuery query);
}