package com.semiproject.commons.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class CommonInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        init(request);
        return true;
    }

    // 초기화 작업
    private void init(HttpServletRequest request) {
        HttpSession session = request.getSession();

        /* PC, Mobile 수동 변경 처리 Start*/
        String device = request.getParameter("device");
        if(device != null && !device.isBlank()) {
           session.setAttribute("deivce", device.toLowerCase()
                   .equals("MOBILE") ? "mobile" : "pc");
        }

        /* PC, Mobile 수동 변경 처리 End*/

    }
}
