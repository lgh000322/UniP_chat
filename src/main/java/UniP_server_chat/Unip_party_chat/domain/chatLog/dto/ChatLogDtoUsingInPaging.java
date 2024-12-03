package UniP_server_chat.Unip_party_chat.domain.chatLog.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ChatLogDtoUsingInPaging(List<ChatLogDto> chatLogDtos, LocalDateTime timeToPage) {

    public static ChatLogDtoUsingInPaging setChatLogDtoUsingInPaging(List<ChatLogDto> chatLogDtos, LocalDateTime timeToPage) {
        return ChatLogDtoUsingInPaging.builder()
                .chatLogDtos(chatLogDtos)
                .timeToPage(timeToPage)
                .build();
    }
}
