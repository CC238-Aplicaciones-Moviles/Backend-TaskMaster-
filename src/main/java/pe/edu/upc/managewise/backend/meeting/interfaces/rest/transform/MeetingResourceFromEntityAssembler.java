package pe.edu.upc.managewise.backend.meeting.interfaces.rest.transform;

import pe.edu.upc.managewise.backend.meeting.domain.model.aggregates.Meeting;
import pe.edu.upc.managewise.backend.meeting.interfaces.rest.resources.MeetingResource;

public class MeetingResourceFromEntityAssembler {

  public static MeetingResource toResourceFromEntity(Meeting entity) {
    return new MeetingResource(
      entity.getId(),
      entity.getTitle(),
      entity.getMeetingDate().toString(),
      entity.getMeetingTime().toString(),
      entity.getLink(),
      entity.getAccessCode(),
      entity.getHostId(),
      entity.getMembers() // No se necesita mapear, ya que es una lista de Long
    );
  }
}
