package UniP_server_chat.Unip_party_chat.domain.chatLog.dto;

import lombok.Builder;

@Builder
public record ChatLogResponse(String sender,String content,String participantImageUrl, boolean isLeft) {

}
