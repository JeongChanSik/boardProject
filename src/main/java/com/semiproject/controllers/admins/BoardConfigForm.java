package com.semiproject.controllers.admins;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class BoardConfigForm {
/**
* 이 클래스는 커맨드 객체다.
**/

    private String mode;

    @NotBlank(message = "게시판 아이디를 입력하세요.")
    private String bId; // 게시판 아이디

    @NotBlank(message = "게시판 이름을 입력하세요.")
    private String bName; // 게시판 이름

    private boolean active; // 활성화 여부

    private String authority = "ALL"; // 기본 값은 ALL 설정

    private String category; // 게시판 분류
}
