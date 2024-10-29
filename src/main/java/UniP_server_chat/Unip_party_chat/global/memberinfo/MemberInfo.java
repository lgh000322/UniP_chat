package UniP_server_chat.Unip_party_chat.global.memberinfo;


import org.springframework.stereotype.Component;

@Component
public class MemberInfo {

    private final ThreadLocal<String> username = new ThreadLocal<>();

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public void clear() {
        username.remove();
    }

}
