package pe.edu.upc.managewise.backend.project.interfaces;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.managewise.backend.project.domain.model.commands.CreateProjectCommand;
import pe.edu.upc.managewise.backend.project.domain.model.commands.DeleteProjectCommand;
import pe.edu.upc.managewise.backend.project.domain.model.queries.GetAllProjectsQuery;
import pe.edu.upc.managewise.backend.project.domain.model.queries.GetProjectByIdQuery;
import pe.edu.upc.managewise.backend.project.domain.model.queries.GetProjectsByUserIdQuery;
import pe.edu.upc.managewise.backend.project.domain.services.ProjectCommandService;
import pe.edu.upc.managewise.backend.project.domain.services.ProjectQueryService;
import pe.edu.upc.managewise.backend.project.interfaces.rest.resources.CreateProjectResource;
import pe.edu.upc.managewise.backend.project.interfaces.rest.resources.ProjectResource;
import pe.edu.upc.managewise.backend.project.interfaces.rest.transform.CreateProjectCommandFromResourceAssembler;
import pe.edu.upc.managewise.backend.project.interfaces.rest.transform.ProjectResourceFromEntityAssembler;
import pe.edu.upc.managewise.backend.project.interfaces.rest.transform.UpdateProjectCommandFromResourceAssembler;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/projects", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Projects", description = "Project Management Endpoints")
public class ProjectsController {

    private final ProjectQueryService projectQueryService;
    private final ProjectCommandService projectCommandService;

    public ProjectsController(ProjectQueryService projectQueryService, ProjectCommandService projectCommandService) {
        this.projectQueryService = projectQueryService;
        this.projectCommandService = projectCommandService;
    }

    // Endpoint to create a new project
    @PostMapping("/user/{userId}")
    public ResponseEntity<ProjectResource> createProject(@PathVariable Long userId, @RequestBody CreateProjectResource resource) {
        // Ahora userId se recibe como parte de la URL
        // Asignamos el userId del PathVariable al comando de creación
        var createProjectCommand = CreateProjectCommandFromResourceAssembler.toCommandFromResource(resource);

        // Aseguramos que el userId esté presente en el comando
        createProjectCommand = new CreateProjectCommand(
                userId,  // Asignamos el userId recibido en la URL
                createProjectCommand.name(),
                createProjectCommand.description(),
                createProjectCommand.endDate(),
                createProjectCommand.status()
        );

        var projectId = this.projectCommandService.handle(createProjectCommand);

        if (projectId.equals(0L)) {
            return ResponseEntity.badRequest().build();
        }

        var getProjectByIdQuery = new GetProjectByIdQuery(projectId);
        var optionalProject = this.projectQueryService.handle(getProjectByIdQuery);

        if (optionalProject.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var projectResource = ProjectResourceFromEntityAssembler.toResourceFromEntity(optionalProject.get());
        return new ResponseEntity<>(projectResource, HttpStatus.CREATED);
    }


    // Endpoint to get all projects
    @GetMapping
    public ResponseEntity<List<ProjectResource>> getAllProjects() {
        var getAllProjectsQuery = new GetAllProjectsQuery();
        var projects = this.projectQueryService.handle(getAllProjectsQuery);
        var projectResources = projects.stream()
                .map(ProjectResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(projectResources);
    }

    // Endpoint to get a project by its ID
    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResource> getProjectById(@PathVariable Long projectId) {
        var getProjectByIdQuery = new GetProjectByIdQuery(projectId);
        var optionalProject = this.projectQueryService.handle(getProjectByIdQuery);
        if (optionalProject.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var projectResource = ProjectResourceFromEntityAssembler.toResourceFromEntity(optionalProject.get());
        return ResponseEntity.ok(projectResource);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProjectResource>> getProjectsByUserId(@PathVariable Long userId) {
        var getProjectsByUserIdQuery = new GetProjectsByUserIdQuery(userId);  // Creamos la consulta con el userId
        var projects = this.projectQueryService.handle(getProjectsByUserIdQuery);

        if (projects.isEmpty()) {
            return ResponseEntity.notFound().build();  // Si no se encuentran proyectos, devolvemos 404
        }

        // Convertimos las entidades a ProjectResource para la respuesta
        var projectResources = projects.stream()
                .map(ProjectResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(projectResources);  // Retorna los proyectos en formato Resource
    }


    // Endpoint to update an existing project
    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectResource> updateProject(@PathVariable Long projectId, @RequestBody ProjectResource resource) {
        var updateProjectCommand = UpdateProjectCommandFromResourceAssembler.toCommandFromResource(projectId, resource);
        var optionalProject = this.projectCommandService.handle(updateProjectCommand);

        if (optionalProject.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var projectResource = ProjectResourceFromEntityAssembler.toResourceFromEntity(optionalProject.get());
        return ResponseEntity.ok(projectResource);
    }

    // Endpoint to delete a project
    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable Long projectId) {
        var deleteProjectCommand = new DeleteProjectCommand(projectId);
        this.projectCommandService.handle(deleteProjectCommand);
        return ResponseEntity.noContent().build();
    }
}
