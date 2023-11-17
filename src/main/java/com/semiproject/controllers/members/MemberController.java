package com.semiproject.controllers.members;

import com.semiproject.commons.MemberUtil;
import com.semiproject.commons.Utils;
import com.semiproject.commons.constant.MemberType;
import com.semiproject.entities.BoardData;
import com.semiproject.entities.Member;
import com.semiproject.models.member.MemberInfo;
import com.semiproject.repositories.BoardDataRepository;
import com.semiproject.repositories.MemberRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final Utils utils;
    private final MemberUtil memberUtil;
    private final EntityManager em;

    @Autowired
    private BoardDataRepository boardDataRepository;

    @Autowired
    private MemberRepository memberRepository;

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
        Member member = Member.builder()
                .email("user01@test.org")
                .password("123456")
                .userNm("사용자01")
                .mtype(MemberType.USER)
                .build();
        memberRepository.saveAndFlush(member);

        BoardData item = BoardData.builder()
                .subject("제목")
                .content("내용")
                .member(member)
                .build();
        boardDataRepository.saveAndFlush(item);

        em.clear();

        BoardData data = boardDataRepository.findById(1L).orElse(null);

        Member member2 = data.getMember();
        String email = member2.getEmail(); // 2차 쿼리 실행 (지연 로딩)
        System.out.println(email);

    }

    @ResponseBody
    @GetMapping("/info2")
    public void info2() {
        Member member = Member.builder()
                .email("user01@test.org")
                .password("123456")
                .userNm("사용자01")
                .mtype(MemberType.USER)
                .build();
        memberRepository.saveAndFlush(member);

        List<BoardData> items = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {


            BoardData item = BoardData.builder()
                    .subject("제목" + i)
                    .content("내용" + i)
                    .member(member)
                    .build();
            items.add(item);
        }

        boardDataRepository.saveAllAndFlush(items);
    }

    @ResponseBody
    @GetMapping("/info3")
    public void info3() {
        List<BoardData> items = boardDataRepository.findAll();
        for(BoardData item : items) {
            Member member = item.getMember();
            String email = member.getEmail();
        }
    }
}
        /*Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        System.out.println("principal : " + principal);

        Member member = memberUtil.getMember();
        if(memberUtil.isLogin()) {
            log.info(member.toString()); // 회원정보 출력
        }


        log.info("로그인 여부 : {}", memberUtil.isLogin());*/



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




