package pe.edu.upc.managewise.backend.backlog.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.managewise.backend.backlog.domain.model.aggregates.Epic;
import pe.edu.upc.managewise.backend.backlog.domain.model.queries.GetAllEpicsQuery;
import pe.edu.upc.managewise.backend.backlog.domain.model.queries.GetEpicByIdQuery;
import pe.edu.upc.managewise.backend.backlog.domain.model.queries.GetEpicsByUserIdQuery;
import pe.edu.upc.managewise.backend.backlog.domain.services.EpicQueryService;
import pe.edu.upc.managewise.backend.backlog.infrastructure.persistence.jpa.repositories.EpicRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EpicQueryServiceImpl implements EpicQueryService {
    private final EpicRepository epicRepository;

    public EpicQueryServiceImpl(EpicRepository epicRepository) {
        this.epicRepository = epicRepository;
    }

    @Override
    public List<Epic> handle(GetAllEpicsQuery query){
        return this.epicRepository.findAll();
    }

    @Override
    public Optional<Epic> handle(GetEpicByIdQuery query){
        return this.epicRepository.findById(query.epicId());
    }

    //epics por userId
    @Override
    public List<Epic> handle(GetEpicsByUserIdQuery query){
        return this.epicRepository.findByUserId(query.userId());
    }
}
