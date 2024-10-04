package UniP_server_chat.Unip_party_chat.domain.chatLog.controller;

import UniP_server_chat.Unip_party_chat.domain.chatLog.dto.ChatMessage;
import UniP_server_chat.Unip_party_chat.domain.chatLog.service.MessageProducer;
import UniP_server_chat.Unip_party_chat.global.baseResponse.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ChatLogController {

    private final MessageProducer messageProducer;
    @MessageMapping("/chat/send/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public ResponseEntity<ResponseDto<?>> sendMessage(@DestinationVariable UUID roomId,
                                                      @Payload ChatMessage chatMessage) {
        messageProducer.sendMessage(roomId, chatMessage); // RabbitMQ에 메시지 전송
        return ResponseEntity.ok().body(ResponseDto.of("메세지 전송 성공", chatMessage));
    }

    @MessageMapping("/chat/addUser/{roomId}")
    @SendTo("/topic/public/{roomId}")
    public ResponseEntity<ResponseDto<?>> addUser(@Payload ChatMessage chatMessage,
                                                  SimpMessageHeaderAccessor headerAccessor,
                                                  @DestinationVariable UUID roomId) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        headerAccessor.getSessionAttributes().put("roomId", roomId);
        return ResponseEntity.ok().body(ResponseDto.of("메세지 전송 성공", chatMessage));
    }

}
