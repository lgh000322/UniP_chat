package UniP_server_chat.Unip_party_chat.domain.chatLog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "채팅 메시지 포맷")
public class ChatMessage {

    @Schema(description = "보낼 채팅내용", example = "보내는 채팅 메시지")
    private String content;
}