package UniP_server_chat.Unip_party_chat.domain.chatRoomParticipant.service;

import UniP_server_chat.Unip_party_chat.domain.chatRoom.entity.ChatRoom;
import UniP_server_chat.Unip_party_chat.domain.chatRoomParticipant.entity.ChatRoomParticipant;
import UniP_server_chat.Unip_party_chat.domain.chatRoomParticipant.repository.ChatRoomParticipantRepository;
import UniP_server_chat.Unip_party_chat.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatRoomParticipantService {
    private final ChatRoomParticipantRepository chatRoomParticipantRepository;
    @Transactional
    public void makeChatRoomParticipants(List<Member> members, ChatRoom chatRoom) {
        for (Member member : members) {
            ChatRoomParticipant chatRoomParticipant=ChatRoomParticipant.builder()
                    .member(member)
                    .chatRoom(chatRoom)
                    .build();

            chatRoomParticipantRepository.save(chatRoomParticipant);
        }
    }
}
