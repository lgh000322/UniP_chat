package UniP_server_chat.Unip_party_chat.domain.chatRoom.dto;


import UniP_server_chat.Unip_party_chat.domain.party.entity.consts.PartyType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record ChatRoomsDto(UUID id,
                           String title,
                           PartyType partyType,
                           @JsonFormat(pattern = "yyyy.MM.dd/HH") LocalDateTime startTime,
                           @JsonFormat(pattern = "yyyy.MM.dd/HH") LocalDateTime endTime,
                           String partyChiefName,
                           String partyChiefImageUrl,
                           Long nowCounted,
                           Integer totalCounted) {



}
