package com.semiproject.commons;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.ResourceBundle;

/**
 * 다양한 유틸리티 메서드를 제공하는 클래스입니다.
 */
@Component
@RequiredArgsConstructor
public class Utils {
    private static ResourceBundle validationsBundle;
    private static ResourceBundle errorsBundle;

    private static ResourceBundle commonsBundle;

    private final HttpServletRequest request;
    private final HttpSession session;

    static {
        validationsBundle = ResourceBundle.getBundle("messages.validations");
        errorsBundle = ResourceBundle.getBundle("messages.errors");
        commonsBundle = ResourceBundle.getBundle("messages.commons");
    }

    /**
     * 지정된 코드와 번들 유형에 해당하는 메시지를 반환합니다.
     *
     * @param code       메시지 코드
     * @param bundleType 번들 유형 ("common", "error", "validation" 중 하나)
     * @return 메시지 문자열
     */
    public static String getMessage(String code, String bundleType) {
        bundleType = Objects.requireNonNull(bundleType, "validation");

        ResourceBundle bundle = null;
        if (bundleType.equals("common")) {
            bundle = commonsBundle;
        } else if (bundleType.equals("error")) {
            bundle = errorsBundle;
        } else {
            bundle = validationsBundle;
        }

        try {
            return bundle.getString(code);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 현재 요청이 모바일 디바이스에서 이루어졌는지 여부를 반환합니다.
     *
     * @return 모바일 디바이스에서 이루어진 요청인 경우 true, 그렇지 않으면 false
     */
    public boolean isMobile() {
        String device = (String) session.getAttribute("device");
        if (device != null) {
            return device.equals("mobile");
        }

        // 요청 헤더 User-Agent
        boolean isMobile = request.getHeader("User-Agent")
                .matches(".*(iPhone|iPod|iPad|BlackBerry|Android|Windows" +
                        " CE|LG|MOT|SAMSUNG|SonyEricsson).*");

        return isMobile;
    }

    /**
     * 모바일 또는 웹에 따른 템플릿 경로를 반환합니다.
     *
     * @param tplPath 템플릿 경로
     * @return 모바일이면 "mobile", 그렇지 않으면 "front"로 시작하는 경로
     */
    public String tpl(String tplPath) {
        String path = String.format("%s/" + tplPath, isMobile() ? "mobile" : "front");
        return path;
    }

    /**
     * 로그인 초기화를 수행하는 메서드입니다.
     *
     * @param session HttpSession 객체
     */
    public static void loginInit(HttpSession session) {
        session.removeAttribute("email");
        session.removeAttribute("NotBlank_email");
        session.removeAttribute("NotBlank_password");
        session.removeAttribute("globalError");
    }

    /**
     * 단일 요청 데이터를 조회하는 메서드입니다.
     *
     * @param name 요청 파라미터 이름
     * @return 요청된 파라미터의 값
     */
    public String getParam(String name) {
        return request.getParameter(name);
    }

    /**
     * 복수개의 요청 데이터를 조회하는 메서드입니다.
     *
     * @param name 요청 파라미터 이름
     * @return 요청된 파라미터의 값 배열
     */
    public String[] getParams(String name) {
        return request.getParameterValues(name);
    }

    /**
     * 주어진 숫자가 음수이면 기본값을 반환하는 메서드입니다.
     *
     * @param num          확인할 숫자
     * @param defaultValue 기본값
     * @return 음수이면 기본값, 그렇지 않으면 주어진 숫자
     */
    public static int getNumber(int num, int defaultValue) {
        return num <= 0 ? defaultValue : num;
    }

    /**
     * 비회원을 구분하는 UID를 생성하는 메서드입니다.
     * 비회원을 구분하는 UID는 IP와 브라우저 종류를 기반으로 합니다.
     *
     * @return 비회원 UID
     */
    public int guestUid() {
        String ip = request.getRemoteAddr();
        String ua = request.getHeader("User-Agent");

        return Objects.hash(ip, ua);
    }
}
