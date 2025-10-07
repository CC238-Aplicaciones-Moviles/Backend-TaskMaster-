package pe.edu.upc.managewise.backend.backlog.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.managewise.backend.backlog.domain.model.commands.CreateSprintCommand;
import pe.edu.upc.managewise.backend.backlog.domain.model.valueobjects.SprintStatus;
import pe.edu.upc.managewise.backend.project.domain.model.aggregates.Project;
import pe.edu.upc.managewise.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.util.Date;

@Getter
@Entity
public class Sprint extends AuditableAbstractAggregateRoot<Sprint> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //private Long userId;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)  // Relacionamos el Sprint con un Proyecto
    private Project project;  // Asociación con el Proyecto

    private String title;
    private String goal;
    private Date startDate;
    private Date endDate;
    private SprintStatus status;

    public Sprint(Project project,/*Long userId,*/ String title, String goal, Date endDate) {
        //this.userId = userId;
        this.project = project;  // Asociamos el Sprint con un Proyecto
        this.title = title;
        this.goal = goal;
        this.startDate = new Date();
        this.endDate = endDate;
        this.status = SprintStatus.STARTED;
    }

    public Sprint() {
    }

    public Sprint(CreateSprintCommand command){
        this();
        this.project = command.project();
        //this.userId = command.userId();
        this.title = command.title();
        this.goal = command.goal();
        this.status = SprintStatus.STARTED;
        this.startDate = new Date();
        this.endDate = command.endDate();
    }
    /*
    public Sprint(String title, String goal, Date endDate) {
        this.title = title;
        this.goal = goal;
        this.startDate = new Date();
        this.endDate = endDate;
        this.status = SprintStatus.STARTED;
    }*/

    public Sprint updateInformation(String title, String goal, SprintStatus status) {
        this.title = title;
        this.goal = goal;
        this.status = status;
        return this;
    }

}
