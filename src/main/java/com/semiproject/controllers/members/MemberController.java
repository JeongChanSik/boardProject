package com.semiproject.controllers.members;

import com.semiproject.commons.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final Utils utils;

    // 회원가입
    @GetMapping("/join")
    public String join() {
        return utils.tpl("member/join");
    }

    // post는 security가 대신 해주기 때문에 안해도 된다.
    @GetMapping("/login")
    public String login() {
        return utils.tpl("member/login");
    }

}


