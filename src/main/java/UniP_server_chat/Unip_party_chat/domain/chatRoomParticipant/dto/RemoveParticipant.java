package UniP_server_chat.Unip_party_chat.domain.chatRoomParticipant.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.util.UUID;

@Builder
public record RemoveParticipant(@NotEmpty(message = "방 번호는 필수입니다.") UUID roomId) {
}
