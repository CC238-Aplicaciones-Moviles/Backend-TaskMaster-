package pe.edu.upc.managewise.backend.task.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.managewise.backend.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import pe.edu.upc.managewise.backend.project.infrastructure.persistence.jpa.repositories.ProjectRepository;
import pe.edu.upc.managewise.backend.task.domain.model.aggregates.Task;
import pe.edu.upc.managewise.backend.task.domain.model.commands.*;
import pe.edu.upc.managewise.backend.task.domain.services.TaskCommandService;
import pe.edu.upc.managewise.backend.task.infrastructure.persistence.jpa.repositories.TaskRepository;

import java.util.Optional;

@Service
public class TaskCommandServiceImpl implements TaskCommandService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public TaskCommandServiceImpl(TaskRepository taskRepository,
                                  ProjectRepository projectRepository,
                                  UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<Task> handle(CreateTaskCommand command) {
        var project = projectRepository.findById(command.projectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        var assignedUsers = userRepository.findAllById(command.assignedUserIds());
        if (assignedUsers.size() != command.assignedUserIds().size()) {
            throw new RuntimeException("One or more users not found");
        }

        var task = new Task(
                project,
                command.title(),
                command.description(),
                command.startDate(),
                command.endDate(),
                command.status(),
                command.priority()
        );

        assignedUsers.forEach(task::assignUser);

        var savedTask = taskRepository.save(task);
        return Optional.of(savedTask);
    }

    @Override
    public Optional<Task> handle(UpdateTaskCommand command) {
        var task = taskRepository.findById(command.taskId())
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.updateDetails(
                command.title(),
                command.description(),
                command.endDate(),
                command.priority()
        );

        if (command.status() != null) {
            task.updateStatus(command.status());
        }

        if (command.assignedUserIds() != null) {
            task.getAssignedUsers().clear();

            var assignedUsers = userRepository.findAllById(command.assignedUserIds());
            assignedUsers.forEach(task::assignUser);
        }

        var updatedTask = taskRepository.save(task);
        return Optional.of(updatedTask);
    }

    @Override
    public Optional<Task> handle(UpdateTaskStatusCommand command) {
        var task = taskRepository.findById(command.taskId())
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.updateStatus(command.status());

        var updatedTask = taskRepository.save(task);
        return Optional.of(updatedTask);
    }

    @Override
    public Optional<Task> handle(AssignUserToTaskCommand command) {
        var task = taskRepository.findById(command.taskId())
                .orElseThrow(() -> new RuntimeException("Task not found"));

        var user = userRepository.findById(command.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        task.assignUser(user);

        var updatedTask = taskRepository.save(task);
        return Optional.of(updatedTask);
    }

    @Override
    public Optional<Task> handle(DeleteTaskCommand command) {
        var task = taskRepository.findById(command.taskId())
                .orElseThrow(() -> new RuntimeException("Task not found"));

        taskRepository.delete(task);
        return Optional.of(task);
    }

    @Override
    public void handle(RemoveUserFromTaskCommand command) {
        var task = taskRepository.findById(command.taskId())
                .orElseThrow(() -> new RuntimeException("Task not found"));

        var user = userRepository.findById(command.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        task.removeUser(user);
        taskRepository.save(task);
    }
}
