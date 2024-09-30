package UniP_server_chat.Unip_party_chat.domain.chatRoomParticipant.entity;

import UniP_server_chat.Unip_party_chat.domain.chatRoom.entity.ChatRoom;
import UniP_server_chat.Unip_party_chat.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

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
    @JoinColumn(name = "member_id")
    private Member member;
}
