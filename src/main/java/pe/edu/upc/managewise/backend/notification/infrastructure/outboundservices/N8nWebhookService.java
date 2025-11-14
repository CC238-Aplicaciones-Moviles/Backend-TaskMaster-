package pe.edu.upc.managewise.backend.notification.infrastructure.outboundservices;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import pe.edu.upc.managewise.backend.notification.domain.model.aggregates.Notification;
import pe.edu.upc.managewise.backend.iam.domain.model.aggregates.User;

import java.util.HashMap;
import java.util.Map;

@Service
public class N8nWebhookService {

    private static final String WEBHOOK_URL = "https://rayomil845.app.n8n.cloud/webhook-test/taskmaster-notifications";
    private final RestTemplate restTemplate;

    public N8nWebhookService() {
        this.restTemplate = new RestTemplate();
    }

    public void sendNotificationToWebhook(Notification notification, User user) {
        try {
            Map<String, Object> payload = new HashMap<>();
            payload.put("userId", notification.getUserId());
            payload.put("userEmail", user.getEmail());
            payload.put("userName", user.getName());
            payload.put("userLastName", user.getLastName());
            payload.put("notificationTitle", notification.getTitle());
            payload.put("notificationMessage", notification.getMessage());
            payload.put("sentAt", notification.getSentAt().toString());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

            restTemplate.postForEntity(WEBHOOK_URL, request, String.class);

            System.out.println("[N8nWebhook] Notification sent to webhook: " + WEBHOOK_URL);
        } catch (Exception e) {
            System.err.println("[N8nWebhook] Error sending notification to webhook: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
