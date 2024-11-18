package UniP_server_chat.Unip_party_chat.domain.member.repository;

import java.util.Optional;

public interface MemberRepositoryCustom {
    Optional<String> findImageUrlByMemberId(Long memberId);
}
