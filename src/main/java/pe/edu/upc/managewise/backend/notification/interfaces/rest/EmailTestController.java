package pe.edu.upc.managewise.backend.notification.interfaces.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.upc.managewise.backend.notification.application.internal.services.EmailService;

@RestController
@RequestMapping("/api/v1/email-test")
public class EmailTestController {

    private final EmailService emailService;

    public EmailTestController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping
    public ResponseEntity<String> sendTest(@RequestParam String to) {
        try {
            emailService.sendNotificationEmail(
                    to,
                    "Prueba TaskMaster PROD",
                    "Si lees esto, el correo en Render está funcionando."
            );
            return ResponseEntity.ok("Correo enviado a " + to);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}