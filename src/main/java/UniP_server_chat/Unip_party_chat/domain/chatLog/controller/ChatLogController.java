package UniP_server_chat.Unip_party_chat.domain.chatLog.controller;

import UniP_server_chat.Unip_party_chat.domain.chatLog.dto.ChatLogDto;
import UniP_server_chat.Unip_party_chat.domain.chatLog.dto.ChatMessage;
import UniP_server_chat.Unip_party_chat.domain.chatLog.service.ChatLogService;
import UniP_server_chat.Unip_party_chat.domain.chatLog.service.MessageProducer;
import UniP_server_chat.Unip_party_chat.global.baseResponse.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ChatLogController {
    private final MessageProducer messageProducer;
    private final ChatLogService chatLogService;

    @MessageMapping("/chat/send/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public ChatMessage sendMessage(@DestinationVariable UUID roomId,
                                   @Payload ChatMessage chatMessage) {
        messageProducer.sendMessage(roomId, chatMessage); // RabbitMQ에 메시지 전송
        return chatMessage;
    }

    @MessageMapping("/chat/addUser/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

    @GetMapping("/chat/logs/{roomId}")
    public ResponseEntity<ResponseDto<?>> getChatLogs(@PathVariable(name = "roomId") UUID roomId,
                                                      Pageable pageable) {
        return ResponseEntity.ok().body(ResponseDto.of("채팅 기록 조회 성공.", chatLogService.findById(roomId, pageable)));
    }
}
