package com.semiproject.controllers.members;

import com.semiproject.commons.CommonProcess;
import com.semiproject.commons.MemberUtil;
import com.semiproject.commons.Utils;
import com.semiproject.entities.Member;
import com.semiproject.models.member.MemberSaveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController implements CommonProcess {

    private final Utils utils;
    // private final MemberUtil memberUtil; 필요 없음
    private final MemberSaveService saveService;

    @GetMapping("/join")
    public String join(@ModelAttribute RequestJoin form, Model model) {
        commonProcess(model, "회원가입");

        return utils.tpl("member/join");
    }

    @PostMapping("/join")
    public String joinps(@Valid RequestJoin form, Errors errors, Model model) {
        commonProcess(model, "회원가입");
        saveService.join(form, errors);
        if(errors.hasErrors()) {
            return utils.tpl("member/join");
        }
        return "redirect:/member/login";
    }

    @GetMapping("/login")
    public String login(String redirectURL, Model model) {
        commonProcess(model, "로그인");

        model.addAttribute("redirectURL", redirectURL);

        return utils.tpl("member/login");
    }


    /*@ResponseBody
    @GetMapping("/info")
    public void info() {

        Member member = memberUtil.getMember();
        if (memberUtil.isLogin()) {
            log.info(member.toString());
        }

        log.info("로그인 여부 : {}", memberUtil.isLogin());
    }*/
    /*
    public void info() {
        MemberInfo member = (MemberInfo)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        log.info(member.toString());
    }
    */
    /*
    public void info(@AuthenticationPrincipal MemberInfo memberInfo) {
        log.info(memberInfo.toString());
    */
    /*
    public void info(Principal principal) {
        String email = principal.getName();
        log.info(email);
    }
     */
}




