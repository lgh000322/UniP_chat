package UniP_server_chat.Unip_party_chat.domain.chatLog.entity;

import UniP_server_chat.Unip_party_chat.domain.chatRoom.entity.ChatRoom;
import UniP_server_chat.Unip_party_chat.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Builder
@Getter
public class ChatLogMongo {

    @Id
    private ObjectId id;

    private UUID chatRoomId;

    private String senderOauthName;

    private String content;

    private LocalDateTime sentTime;
}
