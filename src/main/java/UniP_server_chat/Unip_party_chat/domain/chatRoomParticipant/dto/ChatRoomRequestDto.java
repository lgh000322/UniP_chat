package UniP_server_chat.Unip_party_chat.domain.chatRoomParticipant.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ChatRoomRequestDto {
    private List<String> username;
    private UUID roomId;
}
