package UniP_server_chat.Unip_party_chat.domain.chatLog.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ChatLogBroadCastQueueResponse(String sender, String content, String senderImageUrl, UUID roomId) {
}
