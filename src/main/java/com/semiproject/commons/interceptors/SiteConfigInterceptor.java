package com.semiproject.commons.interceptors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.semiproject.commons.configs.ConfigInfoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 이 인터셉터는 사이트 설정을 유지하기 위해 사용됩니다.
 */
@Component("siteConf")
@RequiredArgsConstructor
public class SiteConfigInterceptor implements HandlerInterceptor {

    private final ConfigInfoService infoService;
    private final HttpServletRequest request;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 요청 URI를 가져옴
        String URI = request.getRequestURI();

        // 제외할 확장자 목록을 정의
        List<String> excludes = Arrays.asList(".css", ".js", ".png", ".jpg", ".jpeg", ".pdf", ".gif", ".xls", ".xlsx");

        // URI에 제외할 확장자가 포함되어 있는지 확인
        boolean matched = excludes.stream().anyMatch(URI::contains);

        // 제외할 확장자가 포함되어 있다면 처리를 종료
        if (matched) {
            return true;
        }

        /** 사이트 설정 조회 */
        // "config" 코드에 해당하는 설정 정보를 가져옴
        Map<String, String> siteConfigs = infoService.get("config", new TypeReference<Map<String, String>>() {});

        // 요청 속성에 사이트 설정을 저장
        request.setAttribute("siteConfig", siteConfigs);
        return true;
    }

    /**
     * 지정된 이름에 해당하는 사이트 설정 값을 가져옴
     *
     * @param name 설정 항목의 이름
     * @return 설정 값
     */
    public String get(String name) {
        Map<String, String> siteConfig = (Map<String, String>) request.getAttribute("siteConfig");
        String value = siteConfig == null ? "" : siteConfig.get(name);

        return value;
    }
}
