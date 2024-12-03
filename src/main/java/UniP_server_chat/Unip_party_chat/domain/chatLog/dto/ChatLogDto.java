package UniP_server_chat.Unip_party_chat.domain.chatLog.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ChatLogDto(String sender, String content, String participantImageUrl, LocalDateTime pagingTime) {

    public static ChatLogDto setChatLogDto(String name, String content, String profileImage, LocalDateTime sentTime){
        ChatLogDto chatLogDto = ChatLogDto.builder()
                .sender(name)
                .content(content)
                .participantImageUrl(profileImage)
                .pagingTime(sentTime)
                .build();

    }
}
