// src/main/java/pe/edu/upc/managewise/backend/meeting/domain/model/valueobjects/MeetingTime.java
package pe.edu.upc.managewise.backend.meeting.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

@Embeddable
public class MeetingTime {

    private LocalTime time;

    // Constructor sin argumentos requerido por JPA
    protected MeetingTime() {
    }

    // Constructor con validación
    public MeetingTime(LocalTime time) {
        if (time == null) {
            throw new IllegalArgumentException("Time cannot be null");
        }
        this.time = time;
    }

    // Método de fábrica para crear MeetingTime a partir de una cadena
    public static MeetingTime of(String timeStr) {
        try {
            LocalTime time = LocalTime.parse(timeStr); // Asegúrate de que sea un formato válido
            return new MeetingTime(time);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid time format: " + timeStr);
        }
    }

    public LocalTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        return time.toString(); // Formato ISO 8601
    }
}