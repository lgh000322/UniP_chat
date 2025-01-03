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
public class ChatMessageQueueFormat {
    @Schema(description = "보낼 채팅내용", example = "보내는 채팅 메시지")
    private String content;

    @Schema(description = "보내는 사람의 이름", example = "홍길동")
    private String sender;

    @Schema(description = "보내는 사람의 Oauth 이름", example = "google ...")
    private String senderOauthName;


    @Schema(description = "채팅방의 이름", example = "12dfsbauifbew-12i3jikbskldf-asidfkhjbawe")
    private UUID roomId;

    @Schema(description = "보낸 시간")
    @Builder.Default
    private LocalDateTime sendTime = LocalDateTime.now();
}
