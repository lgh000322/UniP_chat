package UniP_server_chat.Unip_party_chat.domain.party.entity;

import UniP_server_chat.Unip_party_chat.domain.member.entity.Member;
import UniP_server_chat.Unip_party_chat.domain.party.entity.consts.PartyType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Party {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String content;

    private int partyLimit;

    private int peopleCount;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private PartyType partyType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Version  // 낙관적 락을 위한 버전 필드 추가
    private int version;

    private boolean isClosed;

    public boolean isPartyFull() {
        return peopleCount >= partyLimit;
    }

}
