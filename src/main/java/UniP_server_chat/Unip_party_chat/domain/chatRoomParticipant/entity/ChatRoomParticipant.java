package UniP_server_chat.Unip_party_chat.domain.chatRoomParticipant.entity;

import UniP_server_chat.Unip_party_chat.domain.chatRoom.entity.ChatRoom;
import UniP_server_chat.Unip_party_chat.domain.chatStore.entity.ChatStore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
public class ChatRoomParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_store_id")
    private ChatStore chatStore;

    // 사용자가 들어왔을 때 채팅로그 번호 대신 시간을 사용
    private LocalDateTime participatedTime;
}
