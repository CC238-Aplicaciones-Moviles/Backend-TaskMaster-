package pe.edu.upc.managewise.backend.members.domain.services;

import pe.edu.upc.managewise.backend.members.domain.model.aggregates.Member;
import pe.edu.upc.managewise.backend.members.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

public interface MemberQueryService {
    List<Member> handle(GetAllMembersQuery query);
    Optional<Member> handle(GetMemberByIdQuery query);
    Optional<Member> handle(GetMembersByRoleQuery query);
    Optional<Member> handle(GetMemberByFullNameQuery query);

    List<Member> handle(GetMembersByUserIdQuery query);
}