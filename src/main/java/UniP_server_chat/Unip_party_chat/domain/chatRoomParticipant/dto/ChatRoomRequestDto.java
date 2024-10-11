package UniP_server_chat.Unip_party_chat.domain.chatRoomParticipant.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ChatRoomRequestDto {
    @NotEmpty(message = "유저 이름은 필수입니다.")
    private List<String> username;

    @NotEmpty(message = "방 이름은 필수입니다.")
    private UUID roomId;
}
