package UniP_server_chat.Unip_party_chat.domain.chatRoom.repository;


import UniP_server_chat.Unip_party_chat.domain.chatRoom.dto.ChatRoomsDto;
import UniP_server_chat.Unip_party_chat.domain.chatRoom.entity.QChatRoom;
import UniP_server_chat.Unip_party_chat.domain.chatStore.entity.ChatStore;
import UniP_server_chat.Unip_party_chat.domain.chatStore.entity.QChatStore;
import UniP_server_chat.Unip_party_chat.domain.member.entity.Member;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

import static UniP_server_chat.Unip_party_chat.domain.chatRoom.entity.QChatRoom.chatRoom;
import static UniP_server_chat.Unip_party_chat.domain.chatStore.entity.QChatStore.chatStore;

public class ChatRoomRepositoryCustomImpl implements ChatRoomRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ChatRoomRepositoryCustomImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<ChatRoomsDto> findAllChatRoomsByChatStore(ChatStore chatStoreParam) {
        return queryFactory
                .select(Projections.constructor(ChatRoomsDto.class,
                        chatRoom.id,
                        chatRoom.title)
                )
                .from(chatRoom)
                .leftJoin(chatStore).on(chatRoom.chatStore.id.eq(chatStoreParam.getId()))
                .fetch();
    }
}
