// 📁 pe/edu/upc/managewise/managewise_members/members/infrastructure/persistence/jpa/repositories/MemberRepository.java
package pe.edu.upc.managewise.backend.members.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.managewise.backend.members.domain.model.aggregates.Member;
import pe.edu.upc.managewise.backend.members.domain.model.valueobjects.ScrumRoles;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
     Optional<Member> findByRole(ScrumRoles role);
     Optional<Member> findByFullName(String fullName);
     boolean existsByFullName(String fullName);

     boolean existsByFullNameAndIdIsNot(String fullName, Long id);
     List<Member> findByUserId(Long userId);
}
