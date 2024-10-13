package UniP_server_chat.Unip_party_chat.domain.chatLog.controller;

import UniP_server_chat.Unip_party_chat.domain.chatLog.dto.ChatLogDto;
import UniP_server_chat.Unip_party_chat.domain.chatLog.dto.ChatMessage;
import UniP_server_chat.Unip_party_chat.domain.chatLog.service.ChatLogService;
import UniP_server_chat.Unip_party_chat.domain.chatLog.service.MessageProducer;
import UniP_server_chat.Unip_party_chat.global.baseResponse.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "채팅기록", description = "채팅 기록 관련 API")
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
    @Operation(summary = "채팅 기록 조회", description = "특정 채팅방의 채팅 기록을 가져온다.")
    @ApiResponse(responseCode = "200", description = "채팅 기록 조회 성공",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(type = "array", implementation = ChatLogDto.class)))
    @ApiResponse(responseCode = "400", description = "잘못된 요청")
    @ApiResponse(responseCode = "401", description = "인증 실패")
    public ResponseEntity<ResponseDto<?>> getChatLogs(@PathVariable(name = "roomId") UUID roomId,
                                                      @RequestParam Pageable pageable) {
        return ResponseEntity.ok().body(ResponseDto.of("채팅 기록 조회 성공.", chatLogService.findById(roomId, pageable)));
    }
}
