package UniP_server_chat.Unip_party_chat.global.filter;

import UniP_server_chat.Unip_party_chat.global.memberinfo.MemberInfo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JWTCheckFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final MemberInfo memberInfo;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = getAccessToken(request);
        //accessToken 유효성 검사
        jwtUtil.validateToken(accessToken);

        try {
            //accessToken에서 username을 가져온다.
            String username = jwtUtil.getUsername(accessToken);

            //threadlocal에 username을 저장한다.
            memberInfo.setUsername(username);

            filterChain.doFilter(request, response);
        } finally {
            memberInfo.clear();
        }
    }

    private static String getAccessToken(HttpServletRequest request) {
        return request.getHeader("access");
    }
}
