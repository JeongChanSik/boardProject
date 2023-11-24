package com.semiproject.tests;

import com.semiproject.entities.Board;
import com.semiproject.repositories.BoardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = "spring.profiles.active=test")
public class BoardConfigTest { // 서버를 끈 상태로 하는 통합 테스트

    @Autowired
    private MockMvc mockMvc; // 기능 동작 여부를 브라우저 없이 가능하다.

    @Autowired
    private BoardRepository boardRepository;

    @Test
    @DisplayName("게시판 설정 저장 테스트")
    void boardConfigTest() throws Exception {
        String body = mockMvc.perform(post("/admin/board/save")
                        // Content-Type : application/x-www.form-url
                        .param("mode", "add")
                        .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isOk()) // isOk() : 성공값 요청 메소드 200
                .andReturn().getResponse()
                .getContentAsString(Charset.forName("UTF-8"));

        assertTrue(body.contains("아이디"));
        assertTrue(body.contains("게시판 이름"));
    }

    @Test
    @DisplayName("게시판 설정 저장 테스트 - 성공시 200")
    void boardConfigTest2() throws Exception {
        mockMvc.perform(post("/admin/board/save")
                .param("bId", "notice")
                .param("bName", "공지사항")
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());

        // 실제 DB에도 설정 값이 있는지 체크
        Board board = boardRepository.findById("notice").orElse(null);
        assertNotNull(board);

        assertTrue(board.getBName().contains("공지사항"));


    }

}
