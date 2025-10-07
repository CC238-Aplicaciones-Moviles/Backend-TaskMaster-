package pe.edu.upc.managewise.backend.members.interfaces.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import pe.edu.upc.managewise.backend.issues.domain.model.commands.DeleteIssueCommand;
import pe.edu.upc.managewise.backend.issues.domain.model.queries.GetAllIssuesQuery;
import pe.edu.upc.managewise.backend.issues.domain.model.queries.GetIssueByIdQuery;
import pe.edu.upc.managewise.backend.issues.domain.services.IssueCommandService;
import pe.edu.upc.managewise.backend.issues.domain.services.IssueQueryService;
import pe.edu.upc.managewise.backend.issues.interfaces.rest.resources.CreateIssueResource;
import pe.edu.upc.managewise.backend.issues.interfaces.rest.resources.EventRecordItemResource;
import pe.edu.upc.managewise.backend.issues.interfaces.rest.resources.IssueResource;
import pe.edu.upc.managewise.backend.issues.interfaces.rest.transform.CreateIssueCommandFromResourceAssembler;
import pe.edu.upc.managewise.backend.issues.interfaces.rest.transform.EventRecordItemResourceFromEntityAssembler;
import pe.edu.upc.managewise.backend.issues.interfaces.rest.transform.IssueResourceFromEntityAssembler;
import pe.edu.upc.managewise.backend.issues.interfaces.rest.transform.UpdateIssueCommandFromResourceAssembler;
import pe.edu.upc.managewise.backend.members.domain.model.aggregates.Member;
import pe.edu.upc.managewise.backend.members.domain.model.commands.DeleteMemberCommand;
import pe.edu.upc.managewise.backend.members.domain.model.commands.UpdateMemberCommand;
import pe.edu.upc.managewise.backend.members.domain.model.queries.GetAllMembersQuery;
import pe.edu.upc.managewise.backend.members.domain.model.queries.GetMemberByIdQuery;
import pe.edu.upc.managewise.backend.members.domain.model.queries.GetMembersByUserIdQuery;
import pe.edu.upc.managewise.backend.members.domain.services.MemberCommandService;
import pe.edu.upc.managewise.backend.members.domain.services.MemberQueryService;
import pe.edu.upc.managewise.backend.members.interfaces.rest.resources.CreateMemberResource;
import pe.edu.upc.managewise.backend.members.interfaces.rest.resources.MemberResource;
import pe.edu.upc.managewise.backend.members.interfaces.rest.transform.CreateMemberCommandFromResourceAssembler;
import pe.edu.upc.managewise.backend.members.interfaces.rest.transform.MemberResourceFromEntityAssembler;
import pe.edu.upc.managewise.backend.members.interfaces.rest.transform.UpdateMemberCommandFromResourceAssembler;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/members", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Members", description = "Member Management Endpoints")
public class MemberController {
    private final MemberQueryService memberQueryService;
    private final MemberCommandService memberCommandService;

    public MemberController(MemberQueryService memberQueryService, MemberCommandService memberCommandService) {
        this.memberQueryService = memberQueryService;
        this.memberCommandService = memberCommandService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<MemberResource> createMember(@PathVariable Long userId, @RequestBody CreateMemberResource resource){
        var createMemberCommand = CreateMemberCommandFromResourceAssembler.toCommandFromResource(userId, resource);
        var memberId = this.memberCommandService.handle(createMemberCommand);

        if(memberId.equals(0L)){
            return ResponseEntity.badRequest().build();
        }

        var getMemberByIdQuery = new GetMemberByIdQuery(memberId);
        var optionMember = this.memberQueryService.handle(getMemberByIdQuery);

        var memberResource = MemberResourceFromEntityAssembler.toResourceFromEntity(optionMember.get());
        return new ResponseEntity<>(memberResource, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MemberResource>> getAllMembers() {
        var getAllMembersQuery = new GetAllMembersQuery();
        var members = this.memberQueryService.handle(getAllMembersQuery);
        var memberResources = members.stream()
                .map(MemberResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(memberResources);
    }

    //get por user Id
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MemberResource>> getMembersByUserId(@PathVariable Long userId) {
        var getMembersByUserIdQuery = new GetMembersByUserIdQuery(userId);
        var members = this.memberQueryService.handle(getMembersByUserIdQuery);
        var memberResources = members.stream()
                .map(MemberResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(memberResources);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberResource> getMemberById(@PathVariable Long memberId) {
        var getMemberByIdQuery = new GetMemberByIdQuery(memberId);
        var optionalMember = this.memberQueryService.handle(getMemberByIdQuery);
        if (optionalMember.isEmpty())
            return ResponseEntity.notFound().build();
        var memberResource = MemberResourceFromEntityAssembler.toResourceFromEntity(optionalMember.get());
        return ResponseEntity.ok(memberResource);
    }

    @PutMapping("/{memberId}")
    public ResponseEntity<MemberResource> updateMember(@PathVariable Long memberId, @RequestBody MemberResource resource) {
        try {

            //Crea el command y el handle
            var updateMemberCommand = UpdateMemberCommandFromResourceAssembler.toCommandFromResource(memberId, resource);
            var optionalMember = this.memberCommandService.handle(updateMemberCommand);

            if (optionalMember.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            var memberResource = MemberResourceFromEntityAssembler.toResourceFromEntity(optionalMember.get());
            return ResponseEntity.ok(memberResource);
        } catch (IllegalArgumentException e) {
            // Manejar errores de validación
            System.err.println("Error updating member: " + e.getMessage());
            return ResponseEntity.badRequest().build();

        } catch (Exception e) {
            // Manejar errores inesperados
            System.err.println("Unexpected error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //annotacion DeleteMapping
    // y establecemos si tendra alguna ruta en el path profileId
    @DeleteMapping("/{memberId}")
    //recordemos quetodo controller es Response Entitiy
    //El delete va acompañado d eun identificador para encontrarloy eliminarlo
    //Cuando es delete no hay respuesta , es vacio o aveces un valor determinado
    //por eso tiene el ?
    public ResponseEntity<?> deleteIssue(@PathVariable Long memberId) {
        //Creamos el Command y el handler
        var deleteMemberCommand = new DeleteMemberCommand(memberId);
        this.memberCommandService.handle(deleteMemberCommand);
        //devolvemos la respuesta
        //aca no debemos enviar mensaje de error,
        // en este caso seria solo el noContent de que no ecnontro el resource
        return ResponseEntity.noContent().build();
    }
}
