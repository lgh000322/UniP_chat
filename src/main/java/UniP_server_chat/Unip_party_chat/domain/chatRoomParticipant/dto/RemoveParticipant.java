package UniP_server_chat.Unip_party_chat.domain.chatRoomParticipant.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record RemoveParticipant(UUID roomId) {
}
