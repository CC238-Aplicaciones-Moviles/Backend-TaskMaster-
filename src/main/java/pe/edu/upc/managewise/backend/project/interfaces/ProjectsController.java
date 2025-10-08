package pe.edu.upc.managewise.backend.project.interfaces;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.managewise.backend.iam.domain.services.UserQueryService;
import pe.edu.upc.managewise.backend.project.domain.model.commands.AddUserToProjectCommand;
import pe.edu.upc.managewise.backend.project.domain.model.commands.CreateProjectCommand;
import pe.edu.upc.managewise.backend.project.domain.model.commands.DeleteProjectCommand;
import pe.edu.upc.managewise.backend.project.domain.model.commands.RemoveUserFromProjectCommand;
import pe.edu.upc.managewise.backend.project.domain.model.queries.GetAllProjectsQuery;
import pe.edu.upc.managewise.backend.project.domain.model.queries.GetProjectByIdQuery;
import pe.edu.upc.managewise.backend.project.domain.model.queries.GetProjectsByUserIdQuery;
import pe.edu.upc.managewise.backend.project.domain.services.ProjectCommandService;
import pe.edu.upc.managewise.backend.project.domain.services.ProjectQueryService;
import pe.edu.upc.managewise.backend.project.interfaces.rest.resources.CreateProjectResource;
import pe.edu.upc.managewise.backend.project.interfaces.rest.resources.ProjectResource;
import pe.edu.upc.managewise.backend.project.interfaces.rest.resources.UpdateProjectResource;
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
    private final UserQueryService userQueryService;

    public ProjectsController(ProjectQueryService projectQueryService, ProjectCommandService projectCommandService,UserQueryService userQueryService) {
        this.projectQueryService = projectQueryService;
        this.projectCommandService = projectCommandService;
        this.userQueryService = userQueryService;
    }


    @PostMapping("/user/{userId}")
    public ResponseEntity<ProjectResource> createProject(@PathVariable Long userId, @RequestBody CreateProjectResource resource) {


        var createProjectCommand = CreateProjectCommandFromResourceAssembler.toCommandFromResource(resource);


        createProjectCommand = new CreateProjectCommand(
                userId,
                createProjectCommand.name(),
                createProjectCommand.description(),
                createProjectCommand.endDate(),
                createProjectCommand.budget()
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





    @PostMapping("/{projectId}/users")
    public ResponseEntity<?> addUserToProject(@PathVariable Long projectId, @RequestBody Long userId) {
        try {

            AddUserToProjectCommand command = new AddUserToProjectCommand(projectId, userId);
            projectCommandService.handleAddUserToProject(command);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error adding user to project: " + e.getMessage());
        }
    }


    @DeleteMapping("/{projectId}/users/{userId}")
    public ResponseEntity<?> removeUserFromProject(@PathVariable Long projectId, @PathVariable Long userId) {
        try {

            RemoveUserFromProjectCommand command = new RemoveUserFromProjectCommand(projectId, userId);
            projectCommandService.handleRemoveUserFromProject(command);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error removing user from project: " + e.getMessage());
        }
    }


    @GetMapping
    public ResponseEntity<List<ProjectResource>> getAllProjects() {
        var getAllProjectsQuery = new GetAllProjectsQuery();
        var projects = this.projectQueryService.handle(getAllProjectsQuery);
        var projectResources = projects.stream()
                .map(ProjectResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(projectResources);
    }


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
        var getProjectsByUserIdQuery = new GetProjectsByUserIdQuery(userId);
        var projects = this.projectQueryService.handle(getProjectsByUserIdQuery);

        if (projects.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var projectResources = projects.stream()
                .map(ProjectResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(projectResources);
    }


    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectResource> updateProject(@PathVariable Long projectId, @RequestBody UpdateProjectResource resource) {

        var updateProjectCommand = UpdateProjectCommandFromResourceAssembler.toCommandFromResource(projectId, resource);


        var optionalProject = this.projectCommandService.handle(updateProjectCommand);


        if (optionalProject.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }


        var projectResource = ProjectResourceFromEntityAssembler.toResourceFromEntity(optionalProject.get());


        return ResponseEntity.ok(projectResource);
    }



    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable Long projectId) {
        var deleteProjectCommand = new DeleteProjectCommand(projectId);
        this.projectCommandService.handle(deleteProjectCommand);
        return ResponseEntity.noContent().build();
    }

}
