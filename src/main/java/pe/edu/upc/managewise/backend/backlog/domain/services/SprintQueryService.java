package pe.edu.upc.managewise.backend.backlog.domain.services;

import pe.edu.upc.managewise.backend.backlog.domain.model.aggregates.Sprint;
import pe.edu.upc.managewise.backend.backlog.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

public interface SprintQueryService {
    Optional<Sprint> handle(GetSprintByIdQuery query);
    List<Sprint> handle(GetAllSprintsQuery query);;
    Optional<Sprint> handle(GetSprintByTittleQuery query);

    List<Sprint> handle(GetSprintsByUserIdQuery query);
    List<Sprint> handle(GetSprintsByProjectIdQuery query);
}
