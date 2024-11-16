package UniP_server_chat.Unip_party_chat.domain.chatRoom.dto;


import UniP_server_chat.Unip_party_chat.domain.party.entity.consts.PartyType;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record ChatRoomsDto(UUID id,
                           String title,
                           PartyType partyType,
                           LocalDateTime startTime,
                           LocalDateTime endTime,
                           String partyChiefName,
                           String partyChiefImageUrl,
                           Long nowCounted,
                           Long totalCounted) {

}
