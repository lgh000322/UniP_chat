package UniP_server_chat.Unip_party_chat.domain.chatRoomParticipant.service;

import UniP_server_chat.Unip_party_chat.domain.chatRoom.entity.ChatRoom;
import UniP_server_chat.Unip_party_chat.domain.chatRoomParticipant.entity.ChatRoomParticipant;
import UniP_server_chat.Unip_party_chat.domain.chatRoomParticipant.repository.ChatRoomParticipantRepository;
import UniP_server_chat.Unip_party_chat.domain.chatStore.entity.ChatStore;
import UniP_server_chat.Unip_party_chat.domain.chatStore.service.ChatStoreService;
import UniP_server_chat.Unip_party_chat.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatRoomParticipantService {
    private final ChatRoomParticipantRepository chatRoomParticipantRepository;
    private final ChatStoreService chatStoreService;

    @Transactional
    public void makeChatRoomParticipants(List<Member> members, ChatRoom chatRoom, boolean isFirst) {
        if (isFirst) {
            addParticipant(chatRoom, null);
        }

        members.forEach(member -> addParticipant(chatRoom, member));
    }

    private void addParticipant(ChatRoom chatRoom, Member member) {
        ChatStore chatStore = (member != null)
                ? chatStoreService.createOrUseChatStore(member)
                : chatStoreService.createOrUseChatStore();

        ChatRoomParticipant chatRoomParticipant = ChatRoomParticipant.builder()
                .chatStore(chatStore)
                .chatRoom(chatRoom)
                .build();

        chatRoomParticipantRepository.save(chatRoomParticipant);
    }
}
