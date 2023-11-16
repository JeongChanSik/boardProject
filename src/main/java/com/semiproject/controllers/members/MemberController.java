package com.semiproject.controllers.members;

import com.semiproject.commons.MemberUtil;
import com.semiproject.commons.Utils;
import com.semiproject.entities.Member;
import com.semiproject.models.member.MemberInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.security.Security;

@Slf4j
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final Utils utils;
    private final MemberUtil memberUtil;

    // 회원가입
    @GetMapping("/join")
    public String join() {
        return utils.tpl("member/join");
    }

    // post는 security가 대신 해주기 때문에 안해도 된다.
    @GetMapping("/login")
    public String login(String redirectURL, Model model) {
        model.addAttribute("redirectURL", redirectURL);
        return utils.tpl("member/login");
    }

    @ResponseBody
    @GetMapping("/info")
    public void info() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        System.out.println("principal : " + principal);

        Member member = memberUtil.getMember();
        if(memberUtil.isLogin()) {
            log.info(member.toString()); // 회원정보 출력
        }


        log.info("로그인 여부 : {}", memberUtil.isLogin());
    }


 /* public void info() { // getAuthentication() : 회원정보가 담겨있는 객체
        MemberInfo member = (MemberInfo)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        log.info(member.toString());
    } */

 /* public void info(@AuthenticationPrincipal MemberInfo memberInfo) {
        log.info(memberInfo.toString());
    } */

 /* public void info(Principal principal){
        String email = principal.getName();
        log.info(email);
    } */

}


