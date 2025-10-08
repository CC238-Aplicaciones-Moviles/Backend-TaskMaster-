package pe.edu.upc.managewise.backend.backlog.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.managewise.backend.backlog.domain.model.commands.DeleteSprintCommand;
import pe.edu.upc.managewise.backend.backlog.domain.model.queries.GetAllSprintsQuery;
import pe.edu.upc.managewise.backend.backlog.domain.model.queries.GetSprintByIdQuery;
import pe.edu.upc.managewise.backend.backlog.domain.model.queries.GetSprintsByProjectIdQuery;
import pe.edu.upc.managewise.backend.backlog.domain.model.queries.GetSprintsByUserIdQuery;
import pe.edu.upc.managewise.backend.backlog.domain.services.SprintCommandService;
import pe.edu.upc.managewise.backend.backlog.domain.services.SprintQueryService;
import pe.edu.upc.managewise.backend.backlog.interfaces.rest.resources.CreateSprintResource;
import pe.edu.upc.managewise.backend.backlog.interfaces.rest.resources.SprintResource;
import pe.edu.upc.managewise.backend.backlog.interfaces.rest.resources.UpdateSprintResource;
import pe.edu.upc.managewise.backend.backlog.interfaces.rest.transform.CreateSprintCommandFromResourceAssembler;
import pe.edu.upc.managewise.backend.backlog.interfaces.rest.transform.SprintResourceFromEntityAssembler;
import pe.edu.upc.managewise.backend.backlog.interfaces.rest.transform.UpdateSprintCommandFromResourceAssembler;
import pe.edu.upc.managewise.backend.project.domain.model.queries.GetProjectByIdQuery;
import pe.edu.upc.managewise.backend.project.domain.services.ProjectQueryService;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping(value = "/api/v1/sprints", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Sprints", description = "Sprint Management Endpoints")
public class SprintsController {
    private final SprintQueryService sprintQueryService;
    private final SprintCommandService sprintCommandService;
    private final ProjectQueryService projectQueryService;

    public SprintsController(SprintQueryService sprintQueryService, SprintCommandService sprintCommandService, ProjectQueryService projectQueryService) {
        this.sprintQueryService = sprintQueryService;
        this.sprintCommandService = sprintCommandService;
        this.projectQueryService = projectQueryService;
    }

    @PostMapping("/project/{projectId}")
    public ResponseEntity<Object> createSprint(@PathVariable Long projectId, @RequestBody CreateSprintResource resource) {
        var project = this.projectQueryService.handle(new GetProjectByIdQuery(projectId));
        if (project.isEmpty()) {
            return ResponseEntity.badRequest().body("El proyecto con ID " + projectId + " no existe.");
        }
        var createSprintCommand = CreateSprintCommandFromResourceAssembler.toCommandFromResource(project.get(), resource);
        try {
            var sprintId = this.sprintCommandService.handle(createSprintCommand);

            if (sprintId.equals(0L)) {
                return ResponseEntity.badRequest().body("Error al crear el Sprint. Inténtalo de nuevo.");
            }
            var getSprintByIdQuery = new GetSprintByIdQuery(sprintId);
            var optionalSprint = this.sprintQueryService.handle(getSprintByIdQuery);

            if (optionalSprint.isEmpty()) {
                return ResponseEntity.badRequest().body("El Sprint con ID " + sprintId + " no se pudo encontrar después de la creación.");
            }
            var sprintResource = SprintResourceFromEntityAssembler.toResourceFromEntity(optionalSprint.get());
            return new ResponseEntity<>(sprintResource, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error inesperado: " + e.getMessage());
        }
    }




    @GetMapping
    public ResponseEntity<List<SprintResource>> getAllSprints() {
        var getAllSprintsQuery = new GetAllSprintsQuery();
        var sprints = this.sprintQueryService.handle(getAllSprintsQuery);
        var sprintResources = sprints.stream()
                .map(SprintResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(sprintResources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SprintResource> getSprintById(@PathVariable Long id) {
        var getSprintByIdQuery = new GetSprintByIdQuery(id);
        var optionalSprint = this.sprintQueryService.handle(getSprintByIdQuery);
        if (optionalSprint.isEmpty())
            return ResponseEntity.badRequest().build();
        var sprintResource = SprintResourceFromEntityAssembler.toResourceFromEntity(optionalSprint.get());
        return ResponseEntity.ok(sprintResource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SprintResource> updateSprint(@PathVariable Long id, @RequestBody UpdateSprintResource resource) {
        var updateSprintCommand = UpdateSprintCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var optionalSprint = this.sprintCommandService.handle(updateSprintCommand);

        if (optionalSprint.isEmpty())
            return ResponseEntity.badRequest().build();
        var sprintResource = SprintResourceFromEntityAssembler.toResourceFromEntity(optionalSprint.get());
        return ResponseEntity.ok(sprintResource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSprint(@PathVariable Long id) {
        var deleteSprintCommand = new DeleteSprintCommand(id);
        this.sprintCommandService.handle(deleteSprintCommand);
        return ResponseEntity.noContent().build();
    }

    //get sprint by userId
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SprintResource>> getSprintsByUserId(@PathVariable Long userId) {
        var sprints = this.sprintQueryService.handle(new GetSprintsByUserIdQuery(userId));
        var sprintResources = sprints.stream()
                .map(SprintResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(sprintResources);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<SprintResource>> getSprintsByProjectId(@PathVariable Long projectId) {

        var project = this.projectQueryService.handle(new GetProjectByIdQuery(projectId));
        if (project.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }


        var getSprintsByProjectIdQuery = new GetSprintsByProjectIdQuery(projectId);
        var sprints = this.sprintQueryService.handle(getSprintsByProjectIdQuery);

        if (sprints.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var sprintResources = sprints.stream()
                .map(SprintResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(sprintResources);
    }
}

