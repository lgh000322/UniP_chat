package UniP_server_chat.Unip_party_chat.global.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTCheckFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestUri = request.getRequestURI();

        if (requestUri.matches("^\\/login(?:\\/.*)?$")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인하지 않고 이용하실 수 없습니다.");
            return;
        }
        if (requestUri.matches("^\\/oauth2(?:\\/.*)?$")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "인증받지 않고 이용하실 수 없습니다.");
            return;
        }

        String accessToken = request.getHeader("access");

        // AccessToken이 없거나, 만료된 경우
        if (accessToken == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰이 만료되었거나 없습니다.");
            return;
        }
        if (jwtUtil.isExpired(accessToken)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰이 만료되었습니다.");
            return;
        }

        // 카테고리가 Access인지 확인
        if (!"access".equals(jwtUtil.getCategory(accessToken))) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "맞지 않는 카테고리입니다.");
            return;
        }

        // 학교 인증을 했는지 확인
        if(!jwtUtil.getAuth(accessToken)){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "학교 인증을 하지 않았습니다.");
            return;
        }

        //사용자의 아이디
        String username = jwtUtil.getUsername(accessToken);

        //Todo: 추가로 처리해야됨

    }
}
