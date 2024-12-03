package UniP_server_chat.Unip_party_chat.domain.chatLog.controller;

import UniP_server_chat.Unip_party_chat.domain.chatLog.dto.*;
import UniP_server_chat.Unip_party_chat.domain.chatLog.service.ChatLogService;
import UniP_server_chat.Unip_party_chat.domain.chatLog.service.MessageProduceUtil;
import UniP_server_chat.Unip_party_chat.domain.chatLog.service.MessageProducer;
import UniP_server_chat.Unip_party_chat.domain.member.entity.Member;
import UniP_server_chat.Unip_party_chat.domain.member.service.CustomMemberService;
import UniP_server_chat.Unip_party_chat.global.baseResponse.ResponseDto;
import UniP_server_chat.Unip_party_chat.global.memberinfo.MemberInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "채팅기록", description = "채팅 기록 관련 API")
public class ChatLogController {
    private final ChatLogService chatLogService;

    //메시지 송신은 http로 진행한다.
    @Operation(summary = "채팅 전송", description = "특정 채팅방에 채팅을 전송한다.")
    @PostMapping("/topic/room/{roomId}")
    public ResponseEntity<ResponseDto<?>> sendChat(@PathVariable(name = "roomId") UUID roomId, @RequestBody ChatMessage chatMessage) {
        chatLogService.produceMessages(roomId,chatMessage.getContent());
        return ResponseEntity.ok().body(ResponseDto.of("메시지 전송 성공", null));
    }

    @GetMapping("/chat/logs/{roomId}")
    @Operation(summary = "채팅 기록 조회", description = "특정 채팅방의 채팅 기록을 가져온다.")
    public ResponseEntity<ResponseDto<?>> getChatLogs(@PathVariable(name = "roomId") UUID roomId,
                                                      @RequestBody(required = false) LocalDateTime participatedTimeInChat,
                                                      @RequestBody(required = false) LocalDateTime pagingTime) {
        List<ChatLogDto> chatLogDtos = chatLogService.findById(roomId, participatedTimeInChat, pagingTime);
        LocalDateTime lastPagingTime = chatLogDtos.get(chatLogDtos.size() - 1).pagingTime();

        ChatLogDtoUsingInPaging chatLogDtoUsingInPaging = ChatLogDtoUsingInPaging.setChatLogDtoUsingInPaging(chatLogDtos, lastPagingTime);

        return ResponseEntity.ok().body(ResponseDto.of("채팅 기록 조회 성공.", chatLogDtoUsingInPaging));
    }

}
