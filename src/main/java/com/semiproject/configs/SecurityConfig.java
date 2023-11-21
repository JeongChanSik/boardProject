package com.semiproject.configs;

import com.semiproject.models.member.LoginFailureHandler;
import com.semiproject.models.member.LoginSuccessHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

@Configuration // 설정은 전부 여기서 진행
@EnableConfigurationProperties(FileUploadConfig.class)
public class SecurityConfig {

    @Autowired
    private FileUploadConfig fileUploadConfig;

    @Bean // 관리 객체
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /*인증 설정 - 로그인 Start*/
        http.formLogin(f -> {
            f.loginPage("/member/login")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .successHandler(new LoginSuccessHandler()) // 로그인 성공 시 처리값
                    .failureHandler(new LoginFailureHandler()); // 로그인 실패 시 처리 값

        }); // DSL

        http.logout(c -> { // 로그아웃 주소로 등록이 된다.
           c.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                   .logoutSuccessUrl("/member/login"); // 로그아웃 주소
        });
        /*인증 설정 - 로그인 End*/

        http.headers(c -> {
            c.frameOptions(o -> o.sameOrigin());
        });
        /* 인가 설정 - 접근 통제 Start */
        http.authorizeHttpRequests(c -> {
           c.requestMatchers("/mypage/**").authenticated() // 회원 전용(로그인 한 회원만 접근 가능)
                   //.requestMatchers("/admin/**").hasAuthority("ADMIN") // 관리자 권한만 접근 가능
                   .requestMatchers("/front/css/**",
                           "/front/js/**",
                           "/front/images/**",

                           "/mobile/css/**",
                           "/mobile/js/**",
                           "/mobile/images/**",

                           "/admin/css/**",
                           "/admin/js/**",
                           "/admin/images/**",

                           "/common/css/**",
                           "/common/js/**",
                           "/common/images/**",
                           fileUploadConfig.getUrl() + "**"
                   ).permitAll()
                   .anyRequest().permitAll(); // 나머지 페이지는 권한 필요가 없다.
        });

        // 인증 실패 시 유입되는 부분
        http.exceptionHandling(c -> {
            c.authenticationEntryPoint((req, resp, e) -> {
                String URI = req.getRequestURI();
                if (URI.indexOf("/admin") != -1) { // 관리자 페이지 - 401 에러 응답 코드 발생
                    resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "NOT AUTHORIZED"); // 401 오류 상수로 표현
                } else { // 회원 전용 페이지 (예 - /mypage ) -> 로그인 페이지로 이동
                    String url = req.getContextPath() + "/member/login";
                    resp.sendRedirect(url);
                }
            });
        });
        /* 인가 설정 - 접근 통제 End */
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
