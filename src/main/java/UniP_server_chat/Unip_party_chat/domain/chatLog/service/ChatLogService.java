package UniP_server_chat.Unip_party_chat.domain.chatLog.service;

import UniP_server_chat.Unip_party_chat.domain.chatLog.dto.ChatLogDto;
import UniP_server_chat.Unip_party_chat.domain.chatLog.repository.ChatLogRepository;
import UniP_server_chat.Unip_party_chat.global.exception.custom.CustomException;
import UniP_server_chat.Unip_party_chat.global.exception.errorCode.ChatLogErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatLogService {

    private final ChatLogRepository chatLogRepository;

    public List<ChatLogDto> findById(UUID roomId, Pageable pageable) {
        return chatLogRepository.findById(roomId, pageable)
                .orElseThrow(() -> new CustomException(ChatLogErrorCode.CHAT_LOG_NOT_FOUND));
    }
}
