package pe.edu.upc.managewise.backend.backlog.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.managewise.backend.backlog.domain.model.commands.CreateEpicCommand;
import pe.edu.upc.managewise.backend.backlog.domain.model.valueobjects.Status;
import pe.edu.upc.managewise.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Getter
@Entity
public class Epic extends AuditableAbstractAggregateRoot<Epic> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    //agregar id de usuario
    private Long userId;

    public Epic() {
    }

    public Epic(Long userId, String title, String description) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.status = Status.TO_DO;
    }

    public Epic(CreateEpicCommand command){
        this();
        this.userId = command.userId();
        this.title = command.title();
        this.description = command.description();
    }

    public Epic updateInformation(String title, String description){
        this.title = title;
        this.description = description;
        return this;
    }

    /*
    public Epic updateStatus(Status status){
        this.status = status;
        return this;
    }
    */
}
