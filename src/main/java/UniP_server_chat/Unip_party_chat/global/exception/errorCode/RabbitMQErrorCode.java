package UniP_server_chat.Unip_party_chat.global.exception.errorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum RabbitMQErrorCode implements ErrorCode {
    CANT_SEND(HttpStatus.REQUEST_TIMEOUT,"메시지를 전송할 수 없습니다.");

    private HttpStatus httpStatus;
    private String message;


}
