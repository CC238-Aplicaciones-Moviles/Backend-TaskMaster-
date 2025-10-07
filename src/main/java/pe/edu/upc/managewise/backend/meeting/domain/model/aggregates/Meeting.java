package pe.edu.upc.managewise.backend.meeting.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.managewise.backend.meeting.domain.model.commands.CreateMeetingCommand;
import pe.edu.upc.managewise.backend.meeting.domain.model.valueobjects.MeetingDate;
import pe.edu.upc.managewise.backend.meeting.domain.model.valueobjects.MeetingTime;
import pe.edu.upc.managewise.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "meetings")
public class Meeting extends AuditableAbstractAggregateRoot<Meeting> {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @NotNull
    @NotBlank
    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Getter
    @Embedded
    private MeetingDate meetingDate;

    @Getter
    @Embedded
    private MeetingTime meetingTime;

    @Getter
    @NotNull
    @NotBlank
    @Column(name = "link", nullable = false)
    private String link;

    @Getter
    @Column(name = "access_code", length = 36, nullable = false, unique = true)
    private String accessCode;

    // Métodos setter y getter para los nuevos campos
    // Establecer el ID del host
    @Getter
    @Column(name = "host_id", nullable = false)
    private Long hostId; // Host de la reunión

    // Establecer los miembros de la reunión
    @Setter
    @Getter
    @ElementCollection
    @CollectionTable(name = "meeting_members", joinColumns = @JoinColumn(name = "meeting_id"))
    @Column(name = "member_id")
    private List<Long> members; // Miembros de la reunión

    //---------------------------------------------------
    public Meeting(Long hostId, String title, String dateStr, String timeStr, String link, String participants, String accessCode) {
        this.hostId = hostId;
        this.title = title;
        this.meetingDate = MeetingDate.of(dateStr);
        this.meetingTime = MeetingTime.of(timeStr);
        this.link = link;
        this.accessCode = UUID.randomUUID().toString(); // Genera un UUID único
        this.members = new ArrayList<>(); // Inicializa la lista de miembros
        this.accessCode = accessCode;
    }

    public Meeting(CreateMeetingCommand command) {
        this.hostId = command.hostId();
        this.title = command.title();
        this.meetingDate = MeetingDate.of(command.dateStr());
        this.meetingTime = MeetingTime.of(command.timeStr());
        this.link = command.link();
        this.accessCode = command.accessCode();
        this.accessCode = UUID.randomUUID().toString(); // Genera un UUID único
        this.members = new ArrayList<>(); // Inicializa la lista de miembros
    }


    public Meeting() {}

    public void updateMeeting(String title, String dateStr, String timeStr, String link, String accessCode) {
        this.title = title;
        this.meetingDate = MeetingDate.of(dateStr);
        this.meetingTime = MeetingTime.of(timeStr);
        this.link = link;
        this.accessCode = accessCode;
    }

}

