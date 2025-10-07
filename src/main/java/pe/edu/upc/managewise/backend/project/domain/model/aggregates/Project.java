package pe.edu.upc.managewise.backend.project.domain.model.aggregates;


import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.managewise.backend.project.domain.model.commands.CreateProjectCommand;
import pe.edu.upc.managewise.backend.project.domain.model.commands.UpdateProjectCommand;
import pe.edu.upc.managewise.backend.project.domain.model.valueobjects.ProjectStatus;
import pe.edu.upc.managewise.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.util.Date;

@Getter
@Entity
public class Project extends AuditableAbstractAggregateRoot<Project> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String name;
    private String description;
    private Date startDate;
    private Date endDate;

    @Enumerated(EnumType.STRING) // Esto mapea el enum como un string en la base de datos
    private ProjectStatus status; // Aquí va el estado del proyecto

    public Project(Long userId, String name, String description, Date endDate, ProjectStatus status) {
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.startDate = new Date(); // Fecha de inicio automáticamente cuando se crea el proyecto
        this.endDate = endDate;
        this.status = status != null ? status : ProjectStatus.PLANNED; // Si no se pasa status, por defecto es PLANNED
    }

    public Project() {
    }

    // Constructor que usa el comando CreateProjectCommand
    /*public Project(CreateProjectCommand command) {
        this(command.userId(), command.name(), command.description(), command.endDate(), command.status());
    }*/

    public Project(CreateProjectCommand command) {
        this();
        this.userId = command.userId();
        this.name = command.name();
        this.description = command.description();
        this.startDate = new Date();
        this.endDate = command.endDate();
        this.status = ProjectStatus.PLANNED;
    }

    public Project updateInformation(UpdateProjectCommand command) {
        this.name = command.name();
        this.description = command.description();
        this.status = command.status();
        this.endDate = command.endDate(); // Actualizamos también la fecha de fin si se proporciona
        return this;
    }

    public void changeStatus(ProjectStatus status) {
        this.status = status;
    }

}