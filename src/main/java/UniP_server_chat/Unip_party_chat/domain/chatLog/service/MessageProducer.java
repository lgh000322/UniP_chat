package UniP_server_chat.Unip_party_chat.domain.chatLog.service;

import UniP_server_chat.Unip_party_chat.domain.chatLog.dto.ChatLogBroadCastQueueResponse;
import UniP_server_chat.Unip_party_chat.domain.chatLog.dto.ChatMessageQueueFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageProducer {
    private final RabbitTemplate rabbitTemplate;

    //실제 데이터베이스에 저장 작업을 위임
    public void sendMessage(ChatMessageQueueFormat chatMessage) {
        rabbitTemplate.convertAndSend("chat.storage.queue", chatMessage);
    }

    //모든 서버에 브로드캐스팅방식으로 요청을 위임
    public void sendMessageToServer(ChatLogBroadCastQueueResponse chatMessageQueueFormat) {
        rabbitTemplate.convertAndSend("chat.broadcast.exchange", "chat.broadcast.key", chatMessageQueueFormat);
    }
}
