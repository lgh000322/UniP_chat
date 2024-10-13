package UniP_server_chat.Unip_party_chat.domain.chatRoom.controller;

import UniP_server_chat.Unip_party_chat.domain.chatLog.dto.ChatLogDto;
import UniP_server_chat.Unip_party_chat.domain.chatRoom.dto.ChatRoomsDto;
import UniP_server_chat.Unip_party_chat.domain.chatRoom.dto.MakeChatRooms;
import UniP_server_chat.Unip_party_chat.domain.chatRoom.service.ChatRoomService;
import UniP_server_chat.Unip_party_chat.global.baseResponse.ResponseDto;
import UniP_server_chat.Unip_party_chat.global.memberinfo.MemberInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "채팅방", description = "채팅방 관련 API")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final MemberInfo memberInfo;

    /**
     * 전체 채팅방을 조회
     * @return jwt 회원의 전체채팅방 조회 응답
     */
    @GetMapping("/chat/rooms")
    @Operation(summary = "전체 채팅방 조회", description = "특정 회원의 전체 채팅방을 가져온다.")
    @ApiResponse(responseCode = "200", description = "전체 채팅방 조회 성공",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(type = "array", implementation = ChatRoomsDto.class)))
    @ApiResponse(responseCode = "400", description = "잘못된 요청")
    @ApiResponse(responseCode = "401", description = "인증 실패")
    public ResponseEntity<ResponseDto<?>> getChatRooms() {
        return ResponseEntity.ok().body(ResponseDto.of("전체 채팅방 조회 성공", chatRoomService.getChatRooms()));
    }

    /**
     * 초기 채팅방 생성
     * @param makeChatRooms 요청 데이터
     * @return jwt 회원의 채팅방 생성응답
     */
    @PostMapping("/chat/rooms")
    @Operation(summary = "채팅방 생성 및 참여", description = "초기 채팅방을 생성하거나 스스로 참여한다.")
    @ApiResponse(responseCode = "200", description = "초기 채팅방 생성 성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청")
    @ApiResponse(responseCode = "401", description = "인증 실패")
    public ResponseEntity<ResponseDto<?>> makeChatRoom(@RequestBody @Valid MakeChatRooms makeChatRooms) {
        chatRoomService.makeChatRoomInit(makeChatRooms, memberInfo.getUsername());
        return ResponseEntity.ok().body(ResponseDto.of("채팅방 생성 성공", null));
    }
}
