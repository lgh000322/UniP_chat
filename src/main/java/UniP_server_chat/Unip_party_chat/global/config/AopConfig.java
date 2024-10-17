package UniP_server_chat.Unip_party_chat.global.config;

import UniP_server_chat.Unip_party_chat.global.logTrace.aspect.FilterResponseAspect;
import UniP_server_chat.Unip_party_chat.global.logTrace.aspect.LogTraceAspect;
import UniP_server_chat.Unip_party_chat.global.logTrace.material.LogTrace;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AopConfig {
    @Bean
    public LogTraceAspect logTraceAspect(LogTrace logTrace) {
        return new LogTraceAspect(logTrace);
    }

    @Bean
    public FilterResponseAspect filterResponseAspect(ObjectMapper objectMapper) {
        return new FilterResponseAspect(objectMapper);
    }
}
