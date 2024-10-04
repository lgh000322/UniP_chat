package UniP_server_chat.Unip_party_chat.domain.chatRoom.repository;

import UniP_server_chat.Unip_party_chat.domain.chatRoom.dto.ChatRoomsDto;
import UniP_server_chat.Unip_party_chat.domain.chatStore.entity.ChatStore;

import java.util.List;

public interface ChatRoomRepositoryCustom {
    List<ChatRoomsDto> findAllChatRoomsByChatStore(ChatStore chatStore);
}
