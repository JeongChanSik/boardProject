package com.semiproject.controllers.admins;

import com.semiproject.commons.ScriptExceptionProcess;
import com.semiproject.commons.menus.Menu;
import com.semiproject.models.board.config.BoardConfigSaveService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * 관리자 페이지에서 게시판을 관리하는 컨트롤러 클래스입니다.
 */
@Controller("adminBoardController")
@RequestMapping("/admin/board")
@RequiredArgsConstructor
public class BoardController implements ScriptExceptionProcess {

    private final HttpServletRequest request;
    private final BoardConfigSaveService saveService;

    /**
     * 게시판 목록을 반환하는 메서드입니다.
     *
     * @param model Model 객체
     * @return 관리자 페이지 게시판 목록 뷰 경로
     */
    @GetMapping
    public String list(Model model) {
        commonProcess("list", model);

        return "admin/board/list";
    }

    /**
     * 게시판 등록 폼을 반환하는 메서드입니다.
     *
     * @param form  BoardConfigForm 객체
     * @param model Model 객체
     * @return 관리자 페이지 게시판 등록 뷰 경로
     */
    @GetMapping("/add")
    public String register(@ModelAttribute BoardConfigForm form, Model model) {
        commonProcess("add", model);

        return "admin/board/add";
    }

    /**
     * 게시판 수정 폼을 반환하는 메서드입니다.
     *
     * @param bId   게시판 ID
     * @param model Model 객체
     * @return 관리자 페이지 게시판 수정 뷰 경로
     */
    @GetMapping("/edit/{bId}")
    public String update(@PathVariable String bId, Model model) {
        commonProcess("edit", model);

        return "admin/board/edit";
    }

    /**
     * 게시판 정보를 저장하는 메서드입니다.
     *
     * @param form   BoardConfigForm 객체
     * @param errors Errors 객체
     * @param model  Model 객체
     * @return 게시판 목록으로 리다이렉트하는 뷰 경로
     */
    @PostMapping("/save")
    public String save(@Valid BoardConfigForm form, Errors errors, Model model) {
        String mode = Objects.requireNonNullElse(form.getMode(), "add");
        commonProcess(mode, model);

        if (errors.hasErrors()) {
            return "admin/board/" + mode;
        }

        saveService.save(form);
        return "redirect:/admin/board";
    }

    /**
     * 공통적인 처리를 수행하는 메서드입니다.
     *
     * @param mode  현재 작업 모드
     * @param model Model 객체
     */
    private void commonProcess(String mode, Model model) {
        String pageTitle = "게시판 목록";
        mode = Objects.requireNonNullElse(mode, "list");
        if (mode.equals("add")) pageTitle = "게시판 등록";
        else if (mode.equals("edit")) pageTitle = "게시판 수정";

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("menuCode", "board");
        model.addAttribute("submenus", Menu.gets("board"));
        model.addAttribute("subMenuCode", Menu.getSubMenuCode(request));
    }
}
