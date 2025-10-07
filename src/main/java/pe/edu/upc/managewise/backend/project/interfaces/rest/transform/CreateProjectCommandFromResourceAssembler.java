package pe.edu.upc.managewise.backend.project.interfaces.rest.transform;

import pe.edu.upc.managewise.backend.project.domain.model.commands.CreateProjectCommand;
import pe.edu.upc.managewise.backend.project.interfaces.rest.resources.CreateProjectResource;
import pe.edu.upc.managewise.backend.project.domain.model.valueobjects.ProjectStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateProjectCommandFromResourceAssembler {
    public static CreateProjectCommand toCommandFromResource(CreateProjectResource resource) {
        // Convertimos el estado que viene como String en un ProjectStatus
        ProjectStatus status = ProjectStatus.valueOf(resource.status().toUpperCase());

        // Convertimos el String de endDate a Date
        Date endDate = convertStringToDate(resource.endDate());

        return new CreateProjectCommand(resource.userId(), resource.name(), resource.description(), endDate, status);
    }

    // Método para convertir String a Date
    private static Date convertStringToDate(String dateString) {
        try {
            // Asegúrate de que el formato coincida con el formato de la fecha que recibes
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(dateString); // Convierte el String a Date
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + dateString, e);
        }
    }
}
