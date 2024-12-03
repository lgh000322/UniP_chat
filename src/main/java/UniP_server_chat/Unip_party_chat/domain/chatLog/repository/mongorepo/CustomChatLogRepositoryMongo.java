package UniP_server_chat.Unip_party_chat.domain.chatLog.repository.mongorepo;

import UniP_server_chat.Unip_party_chat.domain.chatLog.dto.ChatLogDto;
import UniP_server_chat.Unip_party_chat.domain.chatLog.entity.ChatLogMongo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomChatLogRepositoryMongo {
    Optional<List<ChatLogMongo>> findChatLogsBeforeEnteringChat(UUID roomId);

    Optional<List<ChatLogMongo>> findChatLogMongoBySentTimeGoe(UUID roomId, LocalDateTime participatedTime,LocalDateTime pagingTime);
}
