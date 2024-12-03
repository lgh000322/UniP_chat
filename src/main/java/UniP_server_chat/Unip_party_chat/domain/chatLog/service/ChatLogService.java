package UniP_server_chat.Unip_party_chat.domain.chatLog.service;

import UniP_server_chat.Unip_party_chat.domain.chatLog.dto.ChatLogDto;
import UniP_server_chat.Unip_party_chat.domain.chatLog.entity.ChatLogMongo;
import UniP_server_chat.Unip_party_chat.domain.chatLog.repository.ChatLogRepository;
import UniP_server_chat.Unip_party_chat.domain.chatLog.repository.mongorepo.ChatLogRepositoryMongo;
import UniP_server_chat.Unip_party_chat.domain.chatRoomParticipant.repository.ChatRoomParticipantRepository;
import UniP_server_chat.Unip_party_chat.domain.member.entity.Member;
import UniP_server_chat.Unip_party_chat.domain.member.service.CustomMemberService;
import UniP_server_chat.Unip_party_chat.global.exception.custom.CustomException;
import UniP_server_chat.Unip_party_chat.global.exception.errorCode.ChatLogErrorCode;
import UniP_server_chat.Unip_party_chat.global.memberinfo.MemberInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatLogService {
    private final ChatRoomParticipantRepository chatRoomParticipantRepository;
    private final MemberInfo memberInfo;
    private final MessageProduceUtil messageProduceUtil;
    private final ChatLogRepositoryMongo chatLogRepositoryMongo;
    private final CustomMemberService customMemberService;


    @Transactional(readOnly = true)
    public List<ChatLogDto> findById(UUID roomId,
                                     LocalDateTime participatedTimeInChat,
                                     LocalDateTime pagingTime) {
        Member member = memberInfo.getThreadLocalMember();
        return getChatLogDtos(roomId, participatedTimeInChat, pagingTime, member);
    }

    private List<ChatLogDto> getChatLogDtos(UUID roomId, LocalDateTime participatedTimeInChat, LocalDateTime pagingTime, Member member) {
        List<ChatLogDto> result;

        if (participatedTimeInChat == null && pagingTime == null) {
            List<ChatLogMongo> chatLogMongos = chatLogRepositoryMongo.findChatLogsBeforeEnteringChat(roomId)
                    .orElseThrow(() -> new CustomException(ChatLogErrorCode.CHAT_LOG_NOT_FOUND));

            result = mapToChatLogDto(chatLogMongos);

            return result;

        } else {
            LocalDateTime participatedTime = chatRoomParticipantRepository.findParticipatedTimeByMember(member.getId());

            List<ChatLogMongo> chatLogMongos = chatLogRepositoryMongo.findChatLogMongoBySentTimeGoe(roomId, participatedTime, pagingTime)
                    .orElseThrow(() -> new CustomException(ChatLogErrorCode.CHAT_LOG_NOT_FOUND));

            result = mapToChatLogDto(chatLogMongos);

            return result;
        }
    }

    private List<ChatLogDto> mapToChatLogDto(List<ChatLogMongo> chatLogMongos) {
        List<ChatLogDto> result = chatLogMongos.stream()
                .map(chatLogMongo -> {
                    Member foundMember = customMemberService.loadUserByUsername(chatLogMongo.getSenderOauthName());
                    ChatLogDto chatLogDto = ChatLogDto.setChatLogDto(foundMember.getName(), chatLogMongo.getContent(), foundMember.getProfile_image(), chatLogMongo.getSentTime());
                    return chatLogDto;
                })
                .collect(Collectors.toList());

        return result;
    }

    @Transactional
    public void bulkSave(List<ChatLogMongo> chatLogs) {
        chatLogRepositoryMongo.saveAll(chatLogs);
    }

    public void produceMessages(UUID roomId, String content) {
        Member member = memberInfo.getThreadLocalMember();
        messageProduceUtil.produceSavingMessage(roomId, member, content);
        messageProduceUtil.produceBroadCastingMessage(roomId, member, content);
    }
}
