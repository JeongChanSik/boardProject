package com.semiproject.configs;

import com.semiproject.models.member.LoginFailureHandler;
import com.semiproject.models.member.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 설정은 전부 여기서 진행
public class SecurityConfig {

    @Bean // 관리 객체
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.formLogin(f -> {
            f.loginPage("/member/login")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .successHandler(new LoginSuccessHandler()) // 로그인 성공 시 처리값
                    .failureHandler(new LoginFailureHandler()); // 로그인 실패 시 처리 값

        }); // DSL

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
