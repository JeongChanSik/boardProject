package com.semiproject.controllers.boards;

import com.semiproject.commons.MemberUtil;
import com.semiproject.commons.ScriptExceptionProcess;
import com.semiproject.commons.Utils;
import com.semiproject.entities.BoardData;
import com.semiproject.models.board.BoardInfoService;
import com.semiproject.models.board.BoardSaveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController implements ScriptExceptionProcess { // 게시판 아이디가 없으면 돌아가면 되기 때문에 ScriptExceptionProcess 만 던져도 된다.?

    private final Utils utils;
    private final MemberUtil memberUtil;
    private final BoardSaveService saveService;
    private final BoardInfoService infoService;

    /**
    * 게시판 글쓰기
    * */
    @GetMapping("/write/{bId}")
    public String write(@PathVariable("bId") String bId, @ModelAttribute BoardForm form, Model model) { // @PathVariable : 경로변수일 때 사용
        commonProcess(bId, "write", model);

        return utils.tpl("board/write");
    }

    /**
    * 게시판 수정
    * */
    @GetMapping("/update/{seq}")
    public String update(@PathVariable("seq") Long seq, Model model) { // 게시글 번호 추가, model 추가

        return utils.tpl("board/update");
    }

    /**
    * 게시글 저장
    * */
    @PostMapping("/save")
    public String save(@Valid BoardForm form, Errors errors, Model model) { // 검증하기 위해 @Valid 추가
        String mode = Objects.requireNonNullElse(form.getMode(), "write");
        String bId = form.getBId();

        commonProcess(bId, mode, model);

        if(errors.hasErrors()){
            return utils.tpl("board/" + mode);
        }
        saveService.save(form);

        return "redirect:/board/list/" + bId;
    }

    /**
    * 게시글 보기
    * */
    @GetMapping("/view/{seq}")
    public String view(@PathVariable("seq") Long seq, Model model) {

        BoardData data = infoService.get(seq);

        model.addAttribute("boardData", data);

        return utils.tpl("board/view");
    }

    /**
    * 게시글 삭제
    * */
    @GetMapping("/delete/{seq}")
    public String delete(@PathVariable Long seq) {
        return "redirect:/board/list/게시판ID";
    }

    // save인지 update인지 확인하기 위해 mode 입력, bId : boardId값
    private void commonProcess(String bId, String mode, Model model) {
        String pageTitle = "게시글 목록";
        if(mode.equals("write")) pageTitle = "게시글 작성";
        else if (mode.equals("update")) pageTitle = "게시글 수정";
        else if (mode.equals("view")) pageTitle = "게시글 제목";

        List<String> addCommonScript = new ArrayList<>();
        List<String> addScript = new ArrayList<>();

        if(mode.equals("write") || mode.equals("update")) {
            addCommonScript.add("ckeditor/ckeditor");
            addCommonScript.add("fileManager");

            addScript.add("board/form");
        }

        model.addAttribute("addCommonScript", addCommonScript);
        model.addAttribute("addScript", addScript);
        model.addAttribute("pageTitle", pageTitle);
    }
}
