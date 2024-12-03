package UniP_server_chat.Unip_party_chat.domain.chatLog.service;

import UniP_server_chat.Unip_party_chat.domain.chatLog.dto.ChatLogBroadCastQueueResponse;
import UniP_server_chat.Unip_party_chat.domain.chatLog.dto.ChatMessageQueueFormat;
import UniP_server_chat.Unip_party_chat.domain.member.entity.Member;
import UniP_server_chat.Unip_party_chat.domain.member.service.CustomMemberService;
import UniP_server_chat.Unip_party_chat.global.exception.custom.CustomException;
import UniP_server_chat.Unip_party_chat.global.exception.errorCode.RabbitMQErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.AmqpException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MessageProduceUtil {
    private final MessageProducer messageProducer;
    private final CustomMemberService customMemberService;

    public void produceSavingMessage(UUID roomId, Member member, String content) {
        ChatLogBroadCastQueueResponse chatLogBroadCastResponse= ChatLogBroadCastQueueResponse.builder()
                .sender(member.getName())
                .content(content)
                .roomId(roomId)
                .senderImageUrl(customMemberService.getImageUrl(member))
                .build();

        try {
            messageProducer.sendMessageToServer(chatLogBroadCastResponse);//RabbitMQ에 메시지 전송(로드 밸런서가 리버스 프록시로 사용중인 다른 서버에도 요청을 보냄)
        } catch (AmqpException e) {
            throw new CustomException(RabbitMQErrorCode.CANT_SEND);
        }
    }

    public void produceBroadCastingMessage(UUID roomId, Member member, String content) {
        ChatMessageQueueFormat chatMessageQueueFormat = ChatMessageQueueFormat.builder()
                .content(content)
                .sender(member.getName())
                .senderOauthName(member.getUsername())
                .roomId(roomId)
                .build();

        try {
            messageProducer.sendMessage(chatMessageQueueFormat); // RabbitMQ에 메시지 전송(데이터베이스 저장을 비동기로 수행)
        } catch (AmqpException e) {
            throw new CustomException(RabbitMQErrorCode.CANT_SEND);
        }
    }
}
