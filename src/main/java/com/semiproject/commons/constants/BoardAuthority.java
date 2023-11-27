package com.semiproject.commons.constants;

import java.util.Arrays;
import java.util.List;

public enum BoardAuthority {

    /**
    * 관리자 게시판 사용 권한 제한을 위한 Enum 클래스
    * */
    ALL("비회원 + 회원 + 관리자"),
    MEMBER("회원 + 관리자"),
    ADMIN("관리자");

    private final String title;
    BoardAuthority(String title) {
        this.title = title;
    }

    public static List<String[]> getList() {
        return Arrays.asList(
            new String[] { ALL.name(), ALL.title },
            new String[] { MEMBER.name(), MEMBER.title},
            new String[] {ADMIN.name(), ADMIN.title}
        );
    }

    public String getTitle() {
        return title;
    }
}
