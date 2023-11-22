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

@Controller
@RequestMapping("/admin/config")
@RequiredArgsConstructor
public class BasicConfigController implements CommonProcess {

    private final ConfigSaveService saveService;
    private final ConfigInfoService infoService;

    private String code = "config";

    @GetMapping
    public String config(Model model) {
        commonProcess(model);

        ConfigForm form = infoService.get(code, ConfigForm.class);
        form = form == null ? new ConfigForm() : form;
        model.addAttribute("configForm", form);

        return "admin/basic/index";
    }

    @PostMapping
    public String configPs(ConfigForm form, Model model) {
        commonProcess(model);

        saveService.save(code, form);
        model.addAttribute("message", Utils.getMessage("저장되었습니다.", "common"));

        return "admin/basic/index";
    }

    private void commonProcess(Model model) {
        model.addAttribute("pageTitle", Utils.getMessage("사이트_설정", "common"));
        model.addAttribute("menuCode", code);
    }
}
