package UniP_server_chat.Unip_party_chat.domain.chatRoomParticipant.controller;

import UniP_server_chat.Unip_party_chat.domain.chatRoom.entity.ChatRoom;
import UniP_server_chat.Unip_party_chat.domain.chatRoom.service.ChatRoomService;
import UniP_server_chat.Unip_party_chat.domain.chatRoomParticipant.dto.ChatRoomRequestDto;
import UniP_server_chat.Unip_party_chat.domain.chatRoomParticipant.dto.RemoveParticipant;
import UniP_server_chat.Unip_party_chat.domain.chatRoomParticipant.service.ChatRoomParticipantService;
import UniP_server_chat.Unip_party_chat.domain.member.entity.Member;
import UniP_server_chat.Unip_party_chat.domain.member.service.CustomMemberService;
import UniP_server_chat.Unip_party_chat.global.baseResponse.ResponseDto;
import UniP_server_chat.Unip_party_chat.global.memberinfo.MemberInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
public class ChatRoomParticipantController {
    private final ChatRoomParticipantService chatRoomParticipantService;
    private final CustomMemberService customMemberService;
    private final ChatRoomService chatRoomService;
    private final MemberInfo memberInfo;

    /**
     * 채팅방에 사용자 초대
     *
     * @param chatRoomRequestDto
     * @return
     */
    @PostMapping("/chat/room/participant")
    public ResponseEntity<ResponseDto<?>> addParticipantSpecificChatRoom(@RequestBody @Valid ChatRoomRequestDto chatRoomRequestDto) {
        List<Member> members = chatRoomRequestDto.getUsername().stream()
                .map(username -> customMemberService.loadUserByUsername(username))
                .collect(Collectors.toList());

        ChatRoom chatRoom = chatRoomService.findById(chatRoomRequestDto.getRoomId());
        chatRoomParticipantService.makeChatRoomParticipants(members, chatRoom, false);
        return ResponseEntity.ok().body(ResponseDto.of("회원 추가 성공", null));
    }

    /**
     * 채팅방 나가기
     */
    @DeleteMapping("/chat/room")
    public ResponseEntity<ResponseDto<?>> deleteParticipantSpecificChatRoom(@RequestBody @Valid RemoveParticipant removeParticipant) {
        Member member = customMemberService.loadUserByUsername(memberInfo.getUsername());
        chatRoomParticipantService.deleteChatRoomParticipant(member, removeParticipant.roomId());
        return ResponseEntity.ok().body(ResponseDto.of("채팅방을 정상적으로 나갔습니다.", null));
    }
}
