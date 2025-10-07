package pe.edu.upc.managewise.backend.backlog.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.managewise.backend.backlog.domain.model.aggregates.Sprint;
import pe.edu.upc.managewise.backend.backlog.domain.model.commands.CreateSprintCommand;
import pe.edu.upc.managewise.backend.backlog.domain.model.commands.DeleteSprintCommand;
import pe.edu.upc.managewise.backend.backlog.domain.model.commands.UpdateSprintCommand;
import pe.edu.upc.managewise.backend.backlog.domain.services.SprintCommandService;
import pe.edu.upc.managewise.backend.backlog.infrastructure.persistence.jpa.repositories.SprintRepository;

import java.util.Optional;

@Service
public class SprintCommandServiceImpl implements SprintCommandService {
    private final SprintRepository sprintRepository;

    public SprintCommandServiceImpl(SprintRepository sprintRepository) {
        this.sprintRepository = sprintRepository;
    }

    @Override
    public Long handle(CreateSprintCommand command){
        var title = command.title();
        if (this.sprintRepository.existsByTitle(title)){
            throw new IllegalArgumentException("Sprint with " + title + " as title already exists");
        }
        var sprint = new Sprint(command);
        try{
            this.sprintRepository.save(sprint);
        } catch (Exception e){
            throw new IllegalArgumentException("Error while saving sprint: " + e.getMessage());
        }
        return sprint.getId();
    }


    @Override
    public Optional<Sprint> handle(UpdateSprintCommand command){
        var sprintId = command.id();
        var title = command.title();
        if(this.sprintRepository.existsByTitleAndIdIsNot(title, sprintId)){
            throw new IllegalArgumentException("Sprint with " + title + " as title already exists");
        }

        if(!this.sprintRepository.existsById(sprintId)){
            throw new IllegalArgumentException("Sprint does not exist");
        }

        var sprintToUpdate = this.sprintRepository.findById(sprintId).get();
        sprintToUpdate.updateInformation(command.title(), command.goal(), command.status());

        try{
            var updatedSprint = this.sprintRepository.save(sprintToUpdate);
            return Optional.of(updatedSprint);
        } catch (Exception e){
            throw new IllegalArgumentException("Error while updating sprint: " + e.getMessage());
        }
    }

    @Override
    public void handle(DeleteSprintCommand command){
        if(!this.sprintRepository.existsById(command.sprintId())){
            throw new IllegalArgumentException("Sprint with id " + command.sprintId() + " does not exist");
        }
        try{
            this.sprintRepository.deleteById(command.sprintId());
        } catch (Exception e){
            throw new IllegalArgumentException("Error while deleting sprint: " + e.getMessage());
        }
    }
}
