package pe.edu.upc.managewise.backend.meeting.application.internal.commandservices;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.managewise.backend.meeting.application.internal.outboundservices.acl.MeetingExternalMemberService;
import pe.edu.upc.managewise.backend.meeting.domain.model.aggregates.Meeting;
import pe.edu.upc.managewise.backend.meeting.domain.model.commands.CreateMeetingCommand;
import pe.edu.upc.managewise.backend.meeting.domain.model.commands.DeleteMeetingCommand;
import pe.edu.upc.managewise.backend.meeting.domain.model.commands.UpdateMeetingCommand;
import pe.edu.upc.managewise.backend.meeting.domain.services.MeetingCommandService;
import pe.edu.upc.managewise.backend.meeting.infrastructure.persistence.jpa.repositories.MeetingRepository;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class MeetingCommandServiceImpl implements MeetingCommandService {
    private final MeetingRepository meetingRepository;
    private final MeetingExternalMemberService externalMemberService;

    @Autowired
    public MeetingCommandServiceImpl(MeetingRepository meetingRepository,MeetingExternalMemberService externalMemberService) {
        this.meetingRepository = meetingRepository;
      this.externalMemberService = externalMemberService;
    }

    @Override
    public Long handle(CreateMeetingCommand command) {
        var meetingTitle = command.title();
        if (this.meetingRepository.existsByTitle(meetingTitle)) {
            throw new IllegalArgumentException("Meeting with title " + meetingTitle + " already exists");
        }

      List<Long> memberIds = externalMemberService.fetchAllMemberIds();
      if (memberIds.isEmpty()) {
        throw new IllegalArgumentException("No members found");
      }

      var meeting = new Meeting(command);

      // Establecer la lista de miembros (todos los miembros)
      meeting.setMembers(memberIds);

      try {
            this.meetingRepository.save(meeting);

        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving meeting: " + e.getMessage());
        }
        return meeting.getId();
    }

    @Override
    public Optional<Meeting> handle(UpdateMeetingCommand command) {
        var meetingId = command.meetingId();
        var meetingTitle = command.title();

        if (this.meetingRepository.existsByTitleAndIdIsNot(meetingTitle, meetingId)) {
            throw new IllegalArgumentException("Meeting with title " + meetingTitle + " already exists");
        }

        // Check if the meeting exists
        if (!this.meetingRepository.existsById(meetingId)) {
            throw new IllegalArgumentException("Meeting with id " + meetingId + " does not exist");
        }

        var meetingToUpdate = this.meetingRepository.findById(meetingId).orElseThrow(() ->
                new IllegalArgumentException("Meeting with id " + meetingId + " does not exist"));
        meetingToUpdate.updateMeeting(command.title(), command.dateStr(), command.timeStr(), command.link(), command.accessCode());

        try {
            var updatedMeeting = this.meetingRepository.save(meetingToUpdate);
            return Optional.of(updatedMeeting);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while updating meeting: " + e.getMessage());
        }
    }

    @Override
    public void handle(DeleteMeetingCommand command) {
        // Check if the meeting exists
        if (!this.meetingRepository.existsById(command.meetingId())) {
            throw new IllegalArgumentException("Meeting with id " + command.meetingId() + " does not exist");
        }

        // Try to delete the meeting
        try {
            this.meetingRepository.deleteById(command.meetingId());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while deleting meeting: " + e.getMessage());
        }
    }
}
