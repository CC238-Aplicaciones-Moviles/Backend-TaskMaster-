package pe.edu.upc.managewise.backend.backlog.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.managewise.backend.backlog.domain.model.commands.CreateSprintCommand;
import pe.edu.upc.managewise.backend.backlog.domain.model.valueobjects.Priority;
import pe.edu.upc.managewise.backend.backlog.domain.model.valueobjects.Status;
import pe.edu.upc.managewise.backend.project.domain.model.aggregates.Project;
import pe.edu.upc.managewise.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.util.Date;
import java.util.List;

@Getter
@Entity
public class Sprint extends AuditableAbstractAggregateRoot<Sprint> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //private Long userId;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private Status status;

    @ElementCollection
    private List<Long> members;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    public Sprint(Project project,/*Long userId,*/ String title, String description, Date endDate, Priority priority) {
        //this.userId = userId;
        this.project = project;
        this.title = title;
        this.description = description;
        this.startDate = new Date();
        this.endDate = endDate;
        this.priority = priority;
    }

    public Sprint() {
    }

    public Sprint(CreateSprintCommand command) {
        this();
        this.project = command.project();
        this.title = command.title();
        this.description = command.description();
        this.status = Status.TO_DO;
        this.startDate = new Date();
        this.endDate = command.endDate();


        this.members = command.userIds();
        this.priority = command.priority();
    }

    /*
    public Sprint(String title, String goal, Date endDate) {
        this.title = title;
        this.goal = goal;
        this.startDate = new Date();
        this.endDate = endDate;
        this.status = SprintStatus.STARTED;
    }*/

    public Sprint updateInformation(String title, String description, Status status) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        return this;
    }


    public void addMember(Long userId) {
        if (!this.project.getUserIds().contains(userId)) {
            throw new IllegalArgumentException("El usuario no está registrado en este proyecto.");
        }

        if (!this.members.contains(userId)) {
            this.members.add(userId);
        } else {
            throw new IllegalArgumentException("El usuario ya es miembro de este Sprint.");
        }
    }

}
