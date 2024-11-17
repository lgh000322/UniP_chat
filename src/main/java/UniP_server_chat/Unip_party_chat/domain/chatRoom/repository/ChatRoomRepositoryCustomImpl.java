package UniP_server_chat.Unip_party_chat.domain.chatRoom.repository;


import UniP_server_chat.Unip_party_chat.domain.chatRoom.dto.ChatRoomsDto;
import UniP_server_chat.Unip_party_chat.domain.chatRoom.entity.ChatRoom;
import UniP_server_chat.Unip_party_chat.domain.chatStore.entity.ChatStore;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static UniP_server_chat.Unip_party_chat.domain.chatRoom.entity.QChatRoom.chatRoom;
import static UniP_server_chat.Unip_party_chat.domain.chatRoomParticipant.entity.QChatRoomParticipant.*;
import static UniP_server_chat.Unip_party_chat.domain.chatStore.entity.QChatStore.chatStore;
import static UniP_server_chat.Unip_party_chat.domain.member.entity.QMember.member;
import static UniP_server_chat.Unip_party_chat.domain.party.entity.QParty.*;
import static com.querydsl.core.types.ExpressionUtils.count;

@RequiredArgsConstructor
public class ChatRoomRepositoryCustomImpl implements ChatRoomRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<List<ChatRoomsDto>> findAllChatRoomsByChatStore(ChatStore chatStoreParam) {

        List<ChatRoomsDto> result = queryFactory
                .select(Projections.constructor(ChatRoomsDto.class,
                        chatRoom.id,
                        chatRoom.title,
                        party.partyType,
                        party.startTime,
                        party.endTime,
                        member.name,
                        member.profile_image,
                        count(chatRoomParticipant.id),
                        party.partyLimit
                        ))
                .from(chatRoom)
                .leftJoin(party).on(chatRoom.party.id.eq(party.id))
                .leftJoin(member).on(party.member.id.eq(member.id))
                .leftJoin(chatRoomParticipant).on(chatRoomParticipant.chatRoom.id.eq(chatRoom.id))
                .leftJoin(chatStore).on(chatRoomParticipant.chatStore.id.eq(chatStore.id))
                .where(chatStore.id.eq(chatStoreParam.getId()).and(chatRoom.isDeleted.eq(false)))
                .groupBy(chatRoom.id)
                .fetch();

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<ChatRoom> findChatRoomByPartyId(Long partyId) {
        ChatRoom findChatRoom = queryFactory
                .select(chatRoom)
                .from(chatRoom)
                .where(chatRoom.party.id.eq(partyId))
                .fetchOne();

        return Optional.ofNullable(findChatRoom);
    }
}
