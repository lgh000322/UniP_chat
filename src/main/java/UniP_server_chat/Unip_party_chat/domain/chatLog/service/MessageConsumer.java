package UniP_server_chat.Unip_party_chat.domain.chatLog.service;

import UniP_server_chat.Unip_party_chat.domain.chatLog.dto.ChatMessage;
import UniP_server_chat.Unip_party_chat.domain.chatLog.entity.ChatLog;
import UniP_server_chat.Unip_party_chat.domain.chatLog.repository.ChatLogRepository;
import UniP_server_chat.Unip_party_chat.domain.chatRoom.entity.ChatRoom;
import UniP_server_chat.Unip_party_chat.domain.chatRoom.service.ChatRoomService;
import UniP_server_chat.Unip_party_chat.domain.member.entity.Member;
import UniP_server_chat.Unip_party_chat.domain.member.service.CustomMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MessageConsumer {
    private final ChatLogRepository chatLogRepository;
    private final ChatRoomService chatRoomService;
    private final CustomMemberService customMemberService;
    private final List<ChatLog> chatLogs = new ArrayList<>();
    private static final int BATCH_SIZE = 100; // 배치 크기 설정

    @RabbitListener(queues = "chat.queue")
    public void receiveMessage(ChatMessage chatMessage) {
        ChatRoom chatRoom = chatRoomService.findById(chatMessage.getRoomId());
        Member member = customMemberService.loadUserByUsername(chatMessage.getSender());

        ChatLog chatLog = ChatLog.builder()
                .chatRoom(chatRoom)
                .member(member)
                .content(chatMessage.getContent())
                .build();

        chatLogs.add(chatLog);

        // 배치 크기에 도달하면 저장
        if (chatLogs.size() >= BATCH_SIZE) {
            saveChatLogs();
        }
    }

    // 남아있는 메시지 저장
    @Scheduled(fixedDelay = 1000) // 1초마다 남아있는 메시지 저장
    public void saveRemainingChatLogs() {
        if (!chatLogs.isEmpty()) {
            saveChatLogs();
        }
    }

    @Transactional
    private void saveChatLogs() {
        chatLogRepository.saveAll(chatLogs); // bulk insert
        chatLogs.clear(); // 리스트 초기화
    }

}
