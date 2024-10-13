package UniP_server_chat.Unip_party_chat.domain.chatRoom.service;

import UniP_server_chat.Unip_party_chat.domain.chatRoom.dto.ChatRoomsDto;
import UniP_server_chat.Unip_party_chat.domain.chatRoom.dto.MakeChatRooms;
import UniP_server_chat.Unip_party_chat.domain.chatRoom.entity.ChatRoom;
import UniP_server_chat.Unip_party_chat.domain.chatRoom.repository.ChatRoomRepository;
import UniP_server_chat.Unip_party_chat.domain.chatRoomParticipant.service.ChatRoomParticipantService;
import UniP_server_chat.Unip_party_chat.domain.chatStore.entity.ChatStore;
import UniP_server_chat.Unip_party_chat.domain.chatStore.service.ChatStoreService;
import UniP_server_chat.Unip_party_chat.domain.member.entity.Member;
import UniP_server_chat.Unip_party_chat.domain.member.service.CustomMemberService;
import UniP_server_chat.Unip_party_chat.global.exception.custom.CustomException;
import UniP_server_chat.Unip_party_chat.global.exception.errorCode.ChatRoomErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatStoreService chatStoreService;
    private final ChatRoomParticipantService chatRoomParticipantService;
    private final CustomMemberService customMemberService;
    @Transactional
    public List<ChatRoomsDto> getChatRooms() {
        ChatStore chatStore = chatStoreService.createOrUseChatStore();

        return chatRoomRepository.findAllChatRoomsByChatStore(chatStore)
                .orElseThrow(() -> new CustomException(ChatRoomErrorCode.CHAT_ROOM_NOT_FOUND));
    }

    @Transactional
    public void makeChatRoomInit(MakeChatRooms makeChatRooms) {
        ChatRoom chatRoom = ChatRoom.builder()
                .title(makeChatRooms.getTitle())
                .isDeleted(false)
                .build();

        ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);

        List<Member> members = makeChatRooms.getUsername().stream()
                .map(username -> customMemberService.loadUserByUsername(username))
                .collect(Collectors.toList());


        chatRoomParticipantService.makeChatRoomParticipants(members, savedChatRoom,true);
    }

    public ChatRoom findById(UUID roomId) {
        return chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new CustomException(ChatRoomErrorCode.CHAT_ROOM_NOT_FOUND));
    }
}
