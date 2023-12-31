package com.semiproject.commons;

import com.semiproject.commons.constants.MemberType;
import com.semiproject.entities.Member;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component // Spring 관리 객체
@RequiredArgsConstructor
public class MemberUtil {
     private final HttpSession session;

     public boolean isLogin() {
         // 로그인 형태 리턴, null이면 로그인이 안 된다.
         return getMember() != null;
     }

    /**
     * 관리자 여부 체크
     * @return
     */
    public boolean isAdmin() {
        return isLogin() && getMember().getMtype() == MemberType.ADMIN;
    }

     public Member getMember() {
         return (Member)session.getAttribute("loginMember");
     }
}
