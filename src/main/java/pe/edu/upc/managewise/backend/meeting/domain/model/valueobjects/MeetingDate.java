// src/main/java/pe/edu/upc/managewise/backend/meeting/domain/model/valueobjects/MeetingDate.java
package pe.edu.upc.managewise.backend.meeting.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Embeddable
public class MeetingDate {

    private LocalDate date;

    // Constructor sin argumentos requerido por JPA
    protected MeetingDate() {
    }

    // Constructor con validación
    public MeetingDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        this.date = date;
    }

    // Método de fábrica para crear MeetingDate a partir de una cadena
    public static MeetingDate of(String dateStr) {
        try {
            LocalDate date = LocalDate.parse(dateStr); // Utiliza el formato ISO 8601
            return new MeetingDate(date);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + dateStr);
        }
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return date.toString(); // Formato ISO 8601
    }
}