package UniP_server_chat.Unip_party_chat.domain.chatRoomParticipant.repository;

import UniP_server_chat.Unip_party_chat.domain.chatRoom.entity.QChatRoom;
import UniP_server_chat.Unip_party_chat.domain.chatRoomParticipant.entity.QChatRoomParticipant;
import UniP_server_chat.Unip_party_chat.domain.member.entity.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.UUID;

import static UniP_server_chat.Unip_party_chat.domain.chatRoom.entity.QChatRoom.chatRoom;
import static UniP_server_chat.Unip_party_chat.domain.chatRoomParticipant.entity.QChatRoomParticipant.chatRoomParticipant;

public class ChatRoomParticipantRepositoryCustomImpl implements ChatRoomParticipantRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ChatRoomParticipantRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Long getCountsAtSpecificChatRoom(UUID roomId) {
        return queryFactory
                .select(chatRoomParticipant.count())
                .from(chatRoomParticipant)
                .leftJoin(chatRoom).on(chatRoomParticipant.chatRoom.id.eq(chatRoom.id))
                .where(chatRoomParticipant.chatRoom.id.eq(roomId))
                .fetchOne();
    }

    @Override
    public void deleteChatRoomParticipantByRoomIdAndMember(UUID roomId, Member member) {
        queryFactory
                .delete(chatRoomParticipant)
                .where(chatRoomParticipant.chatRoom.id.eq(roomId)
                        .and(chatRoomParticipant.chatStore.member.id.eq(member.getId())))
                .execute();
    }
}
