package com.semiproject.models.member;

import com.semiproject.commons.Utils;
import com.semiproject.entities.Member;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Objects;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        HttpSession session = request.getSession();

        Utils.loginInit(session);

        /* 로그인 회원정보 세션 처리 - 편의 */
        MemberInfo memberInfo = (MemberInfo)authentication.getPrincipal();
        Member member = memberInfo.getMember();
        session.setAttribute("loginMember", member);

        /* 로그인 성공 시 페이지 이동
           요청 데이터 redirectURL 값이 있으면 이동하고 없으면 메인 페이지로 이동하도록 설정
        */
        // id와 password에 값이 있으면 URL로 이동하고 없으면 메인 페이지로 이동한다.
        String redirectURL = Objects.requireNonNullElse(request.getParameter("redirectURL"),"/");
        response.sendRedirect(request.getContextPath() + redirectURL);

    }
}
