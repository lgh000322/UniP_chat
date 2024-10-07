package UniP_server_chat.Unip_party_chat.domain.chatLog.repository;


import UniP_server_chat.Unip_party_chat.domain.chatLog.dto.ChatLogDto;
import UniP_server_chat.Unip_party_chat.domain.chatLog.entity.QChatLog;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static UniP_server_chat.Unip_party_chat.domain.chatLog.entity.QChatLog.*;

public class ChatLogRepositoryCustomImpl implements ChatLogRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ChatLogRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<List<ChatLogDto>> findById(UUID roomId, Pageable pageable) {
        List<ChatLogDto> findChatLogs = queryFactory
                .select(Projections.constructor(ChatLogDto.class,
                        chatLog.member.name,
                        chatLog.content
                ))
                .from(chatLog)
                .where(chatLog.chatRoom.id.eq(roomId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return Optional.ofNullable(findChatLogs);
    }
}
