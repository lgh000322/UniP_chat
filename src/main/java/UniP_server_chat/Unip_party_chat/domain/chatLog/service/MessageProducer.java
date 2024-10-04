package UniP_server_chat.Unip_party_chat.domain.chatLog.service;

import UniP_server_chat.Unip_party_chat.domain.chatLog.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageProducer {
    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(UUID roomId, ChatMessage chatMessage) {
        chatMessage.setRoomId(roomId);
        rabbitTemplate.convertAndSend("chat.exchange", "chat.routing.key." + roomId, chatMessage);
    }
}
