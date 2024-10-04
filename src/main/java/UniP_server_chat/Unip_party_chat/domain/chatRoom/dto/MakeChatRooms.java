package UniP_server_chat.Unip_party_chat.domain.chatRoom.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MakeChatRooms {
    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @NotBlank(message = "초기에 초대할 회원을 지정해야 합니다.")
    private List<String> username;
}
