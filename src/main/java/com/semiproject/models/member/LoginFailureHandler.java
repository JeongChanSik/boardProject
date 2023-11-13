package com.semiproject.models.member;

import com.semiproject.commons.Utils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        HttpSession session = request.getSession();

        // 세션을 비우기 위해 필요함
        Utils.loginInit(session);

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        boolean isRequiredFieldCheck = false;

// 비밀번호가 틀리더라도 session에 값을 넣어 아이디가 사라지지 않게 저장해주는 역할 -> login.html 에서 이메일 th:value="${session.email}" 저장
        session.setAttribute("email", email);

        /* 필수 항목 검증 - email, password Start */
        if (email == null || email.isBlank()) {
            session.setAttribute("NotBlank_email", Utils.getMessage("NotBlank.email", "validation"));
            isRequiredFieldCheck = true;
        }

        if (password == null || password.isBlank()) {
            session.setAttribute("NotBlank_password", Utils.getMessage("NotBlank.password", "validation"));
            isRequiredFieldCheck = true;
        }
        /* 필수 항목 검증 - email, password End */

        if (!isRequiredFieldCheck) { // 아이디가 없거나 비밀번호가 잘못된 경우
            session.setAttribute("globalError", Utils.getMessage("Login.fail", "validation"));
        }

        response.sendRedirect(request.getContextPath() + "/member/login");
    }

}
