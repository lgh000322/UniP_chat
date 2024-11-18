package UniP_server_chat.Unip_party_chat.domain.member.repository;

import UniP_server_chat.Unip_party_chat.domain.member.entity.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static UniP_server_chat.Unip_party_chat.domain.member.entity.QMember.*;

@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public Optional<String> findImageUrlByMemberId(Long memberId) {
        String imageUrl = queryFactory.select(member.profile_image)
                .from(member)
                .where(member.id.eq(memberId))
                .fetchOne();

        return Optional.ofNullable(imageUrl);
    }
}
