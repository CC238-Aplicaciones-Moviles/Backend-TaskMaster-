package pe.edu.upc.managewise.backend.backlog.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.managewise.backend.backlog.domain.model.aggregates.Sprint;
import pe.edu.upc.managewise.backend.backlog.domain.model.queries.*;
import pe.edu.upc.managewise.backend.backlog.domain.services.SprintQueryService;
import pe.edu.upc.managewise.backend.backlog.infrastructure.persistence.jpa.repositories.SprintRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SprintQueryServiceImpl implements SprintQueryService {
    private final SprintRepository sprintRepository;

    public SprintQueryServiceImpl(SprintRepository sprintRepository) {
        this.sprintRepository = sprintRepository;
    }

    @Override
    public List<Sprint> handle(GetAllSprintsQuery query){
        return this.sprintRepository.findAll();
    }

    @Override
    public Optional<Sprint> handle(GetSprintByIdQuery query){
        return this.sprintRepository.findById(query.sprintId());
    }

    @Override
    public Optional<Sprint> handle(GetSprintByTittleQuery query) {
        return this.sprintRepository.findByTitle(query.tittle());
    }

    /*@Override
    public List<Sprint> handle(GetSprintsByUserIdQuery query){
        return this.sprintRepository.ProjectUserId(query.userId());
    }*/

    @Override
    public List<Sprint> handle(GetSprintsByUserIdQuery query) {
        // Usamos el método que busca los sprints por el userId del Proyecto
        return this.sprintRepository.findByProjectUserId(query.userId());  // Llamamos al método que busca por userId del Proyecto
    }


    @Override
    public List<Sprint> handle(GetSprintsByProjectIdQuery query) {
        return sprintRepository.findByProjectId(query.projectId());  // Método para obtener los sprints por projectId
    }
}
