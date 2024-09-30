package UniP_server_chat.Unip_party_chat.domain.member.repository;

import UniP_server_chat.Unip_party_chat.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
}
