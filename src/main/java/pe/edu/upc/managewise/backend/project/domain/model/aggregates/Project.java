package pe.edu.upc.managewise.backend.project.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.managewise.backend.project.domain.model.commands.CreateProjectCommand;
import pe.edu.upc.managewise.backend.project.domain.model.commands.UpdateProjectCommand;
import pe.edu.upc.managewise.backend.project.domain.model.valueobjects.ProjectCode;
import pe.edu.upc.managewise.backend.project.domain.model.valueobjects.ProjectStatus;
import pe.edu.upc.managewise.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.util.Date;
import java.util.List;

@Getter
@Entity
public class Project extends AuditableAbstractAggregateRoot<Project> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private ProjectCode projectCode;
    private Long userId;
    @ElementCollection
    private List<Long> userIds;

    private String name;
    private String description;
    private Date startDate;
    private Date endDate;

    private Double budget;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;


    public Project(CreateProjectCommand command) {
        this.projectCode = new ProjectCode();
        this.userId = command.userId();
        this.name = command.name();
        this.description = command.description();
        this.startDate = new Date();
        this.endDate = command.endDate();
        this.budget = command.budget();
        this.status = ProjectStatus.PLANNED;
        this.userIds = List.of(command.userId());
    }

    public Project() {
    }

    public void addUser(Long userId) {
        if (!this.userIds.contains(userId)) {
            this.userIds.add(userId);
        }
    }

    public Project updateInformation(UpdateProjectCommand command) {
        this.name = command.name();
        this.description = command.description();
        this.status = command.status();
        this.endDate = command.endDate();
        return this;
    }
    public void changeStatus(ProjectStatus status) {
        this.status = status;
    }
}
