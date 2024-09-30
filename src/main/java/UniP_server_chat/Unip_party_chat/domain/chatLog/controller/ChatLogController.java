package UniP_server_chat.Unip_party_chat.domain.chatLog.controller;

import UniP_server_chat.Unip_party_chat.domain.chatLog.dto.ChatMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ChatLogController {

    @MessageMapping("/chat/send/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public ChatMessage sendMessage(@DestinationVariable UUID roomId, @Payload ChatMessage chatMessage) {
        //TODO: RabbitMQ 추가해야함

        return chatMessage;
    }

    @MessageMapping("/chat/addUser/{roomId}")
    @SendTo("/topic/public/{roomId}")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

}
