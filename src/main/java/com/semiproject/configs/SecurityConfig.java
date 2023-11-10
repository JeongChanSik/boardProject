package com.semiproject.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 설정은 전부 여기서 진행
public class SecurityConfig {

    @Bean // 관리 객체
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.build();
    }

}
