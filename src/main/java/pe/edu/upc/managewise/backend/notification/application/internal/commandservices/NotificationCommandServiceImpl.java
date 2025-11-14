package pe.edu.upc.managewise.backend.notification.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.managewise.backend.notification.domain.model.aggregates.Notification;
import pe.edu.upc.managewise.backend.notification.domain.model.commands.CreateNotificationCommand;
import pe.edu.upc.managewise.backend.notification.domain.services.NotificationCommandService;
import pe.edu.upc.managewise.backend.notification.infrastructure.persistence.jpa.repositories.NotificationRepository;
import pe.edu.upc.managewise.backend.notification.infrastructure.outboundservices.N8nWebhookService;
import pe.edu.upc.managewise.backend.iam.infrastructure.persistence.jpa.repositories.UserRepository;

import java.util.Date;
import java.util.Optional;

@Service
public class NotificationCommandServiceImpl implements NotificationCommandService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final N8nWebhookService n8nWebhookService;

    public NotificationCommandServiceImpl(NotificationRepository notificationRepository,
                                          UserRepository userRepository,
                                          N8nWebhookService n8nWebhookService) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.n8nWebhookService = n8nWebhookService;
    }

    @Override
    public Optional<Notification> handle(CreateNotificationCommand command) {
        var user = userRepository.findById(command.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));


        var notification = new Notification(
                user.getId(),
                command.title(),
                command.message(),
                new Date()
        );

        var savedNotification = notificationRepository.save(notification);

        System.out.println("[Notification] Guardada notification id=" + savedNotification.getId() + ", userId=" + savedNotification.getUserId() + ", title=" + savedNotification.getTitle());
        
        // Enviar notificación al webhook de n8n
        n8nWebhookService.sendNotificationToWebhook(savedNotification, user);
        
        return Optional.of(savedNotification);
    }
}
