package com.semiproject.controllers.files;

import com.semiproject.models.file.FileDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 파일을 처리하는 컨트롤러 클래스
 */
@Controller("frontFileController")
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final FileDeleteService deleteService;

    /**
     * 파일을 삭제하는 메서드
     *
     * @param id    삭제할 파일의 ID
     * @param model Model 객체
     * @return 파일 삭제 성공 시 콜백 함수를 실행하는 뷰 경로
     */
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {
        deleteService.delete(id);

        /** 파일 삭제 성공시 콜백 함수 처리 */
        String script = String.format("if(typeof parent.fileDeleteCallback == 'function') parent.fileDeleteCallback(%d);", id);
        model.addAttribute("script", script);
        return "common/_execute_script";
    }

    /**
     * 예외 처리 핸들러 메서드
     *
     * @param e     발생한 예외 객체
     * @param model Model 객체
     * @return 에러 메세지를 알림창으로 띄우는 뷰 경로
     */
    @ExceptionHandler(Exception.class)
    public String errorHandler(Exception e, Model model) {

        e.printStackTrace();

        String script = String.format("alert('%s');", e.getMessage());
        model.addAttribute("script", script);
        return "common/_execute_script";
    }
}
