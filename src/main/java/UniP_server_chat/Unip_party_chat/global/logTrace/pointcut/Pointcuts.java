package UniP_server_chat.Unip_party_chat.global.logTrace.pointcut;

import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
public class Pointcuts {

    @Pointcut("execution(* UniP_server_chat.Unip_party_chat.domain..*(..))")
    public void aopMethod() {
    }

    @Pointcut("execution(public UniP_server_chat.Unip_party_chat.domain..sendMessage(..))")
    public void excludeSendMessage() {
    }

    @Pointcut("execution(public UniP_server_chat.Unip_party_chat.domain..addUser(..))")
    public void excludeAddUser() {
    }
}
