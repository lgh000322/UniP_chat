package UniP_server_chat.Unip_party_chat.global.config;

import UniP_server_chat.Unip_party_chat.global.filter.JWTCheckFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<JWTCheckFilter> jwtCheckFilter(JWTCheckFilter jwtCheckFilter) {
        FilterRegistrationBean<JWTCheckFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(jwtCheckFilter);
        registrationBean.addUrlPatterns("/*");

        return registrationBean;
    }
}
