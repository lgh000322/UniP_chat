package UniP_server_chat.Unip_party_chat.domain.chatLog.service;

import UniP_server_chat.Unip_party_chat.domain.chatLog.repository.ChatLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatLogService {

    private final ChatLogRepository chatLogRepository;

}
