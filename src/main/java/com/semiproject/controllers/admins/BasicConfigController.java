package com.semiproject.controllers.admins;

import com.semiproject.commons.CommonProcess;
import com.semiproject.commons.Utils;
import com.semiproject.commons.configs.ConfigInfoService;
import com.semiproject.commons.configs.ConfigSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 관리자 페이지에서 기본 설정을 관리하는 컨트롤러 클래스
 */
@Controller
@RequestMapping("/admin/config")
@RequiredArgsConstructor
public class BasicConfigController implements CommonProcess {

    private final ConfigSaveService saveService;
    private final ConfigInfoService infoService;

    private final String code = "config";

    /**
     * 기본 설정 페이지를 반환하는 메서드
     *
     * @param model Model 객체
     * @return 관리자 페이지 기본 설정 뷰 경로
     */
    @GetMapping
    public String config(Model model) {
        commonProcess(model);

        // 설정 정보를 가져와서 뷰에 전달
        ConfigForm form = infoService.get(code, ConfigForm.class);
        form = form == null ? new ConfigForm() : form;
        model.addAttribute("configForm", form);

        return "admin/basic/index";
    }

    /**
     * 기본 설정을 저장하는 메서드
     *
     * @param form  ConfigForm 객체
     * @param model Model 객체
     * @return 관리자 페이지 기본 설정 뷰 경로
     */
    @PostMapping
    public String configPs(ConfigForm form, Model model) {
        commonProcess(model);

        // 설정 정보를 저장
        saveService.save(code, form);
        model.addAttribute("message", Utils.getMessage("저장되었습니다.", "common"));

        return "admin/basic/index";
    }

    /**
     * 공통적인 처리를 수행하는 메서드
     *
     * @param model Model 객체
     */
    private void commonProcess(Model model) {
        model.addAttribute("pageTitle", Utils.getMessage("사이트_설정", "common"));
        model.addAttribute("menuCode", code);
    }
}
