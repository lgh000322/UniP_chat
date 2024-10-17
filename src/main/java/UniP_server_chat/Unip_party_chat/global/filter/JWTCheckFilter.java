package UniP_server_chat.Unip_party_chat.global.filter;

import UniP_server_chat.Unip_party_chat.global.filter.annotation.FilterResponse;
import UniP_server_chat.Unip_party_chat.global.memberinfo.MemberInfo;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String requestURI = request.getRequestURI();

        // Swagger UI 및 API Docs에 대한 요청을 필터에서 제외
        if (requestURI.startsWith("/swagger-ui") ||
                requestURI.startsWith("/v3/api-docs")) {
            return true;
        }
        return super.shouldNotFilter(request);
    }

    @Override
//    @FilterResponse
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = getAccessToken(request);

        if (accessToken == null) {
            throw new JwtException("토큰을 받지 못했습니다.");
        }

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
