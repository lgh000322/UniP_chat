package UniP_server_chat.Unip_party_chat.domain.party.entity.consts;

import lombok.Getter;

@Getter
public enum PartyType {
    RESTAURANT("식사"),
    BAR("술집"),
    COMPREHENSIVE("종합");
    private final String description;

    PartyType(String description) {
        this.description = description;
    }

}
