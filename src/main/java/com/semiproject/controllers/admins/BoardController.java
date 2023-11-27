package com.semiproject.controllers.admins;

import com.semiproject.commons.ListData;
import com.semiproject.commons.ScriptExceptionProcess;
import com.semiproject.commons.menus.Menu;
import com.semiproject.entities.Board;
import com.semiproject.models.board.config.BoardConfigInfoService;
import com.semiproject.models.board.config.BoardConfigSaveService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller("adminBoardController")
@RequestMapping("/admin/board")
@RequiredArgsConstructor
public class BoardController implements ScriptExceptionProcess {

    private final HttpServletRequest request;
    private final BoardConfigSaveService saveService;
    private final BoardConfigInfoService infoService;

    @GetMapping
    public String list(@ModelAttribute BoardSearch search, Model model) {
        commonProcess("list", model);

        ListData<Board> data = infoService.getList(search);

        model.addAttribute("items", data.getContent());
        model.addAttribute("pagination", data.getPagination());

        return "admin/board/list";
    }

    @GetMapping("/add")
    public String register(@ModelAttribute BoardConfigForm form, Model model) {
        commonProcess("add", model);

        return "admin/board/add";
    }

    @GetMapping("/edit/{bId}")
    public String update(@PathVariable String bId, Model model) {
        commonProcess("edit", model);

        return "admin/board/edit";
    }

    @PostMapping("/save")
    public String save(@Valid BoardConfigForm form, Errors errors, Model model) {
        String mode = Objects.requireNonNullElse(form.getMode(),"add");
        commonProcess(mode, model);

        if(errors.hasErrors()) {
            return "admin/board/" + mode;
        }

        saveService.save(form);
        return "redirect:/admin/board";
    }

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