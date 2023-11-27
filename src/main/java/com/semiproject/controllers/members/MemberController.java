package com.semiproject.controllers.members;

import com.semiproject.commons.CommonProcess;
import com.semiproject.commons.Utils;
import com.semiproject.models.member.MemberSaveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 회원을 관리하는 컨트롤러 클래스입니다.
 */
@Slf4j
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController implements CommonProcess {

    private final Utils utils;
    private final MemberSaveService saveService;

    /**
     * 회원가입 페이지를 반환하는 메서드입니다.
     *
     * @param form  RequestJoin 객체
     * @param model Model 객체
     * @return 회원가입 페이지 뷰 경로
     */
    @GetMapping("/join")
    public String join(@ModelAttribute RequestJoin form, Model model) {
        commonProcess(model, "회원가입");

        return utils.tpl("member/join");
    }

    /**
     * 회원가입을 처리하는 메서드입니다.
     *
     * @param form   RequestJoin 객체
     * @param errors Errors 객체
     * @param model  Model 객체
     * @return 회원가입 성공 시 로그인 페이지로 리다이렉트하는 뷰 경로
     */
    @PostMapping("/join")
    public String joinps(@Valid RequestJoin form, Errors errors, Model model) {
        commonProcess(model, Utils.getMessage("회원가입", "common"));
        saveService.join(form, errors);
        if (errors.hasErrors()) {
            return utils.tpl("member/join");
        }
        return "redirect:/member/login";
    }

    /**
     * 로그인 페이지를 반환하는 메서드입니다.
     *
     * @param redirectURL 이전 페이지로의 리다이렉트 URL
     * @param model       Model 객체
     * @return 로그인 페이지 뷰 경로
     */
    @GetMapping("/login")
    public String login(String redirectURL, Model model) {
        commonProcess(model, Utils.getMessage("로그인", "common"));

        model.addAttribute("redirectURL", redirectURL);

        return utils.tpl("member/login");
    }
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





