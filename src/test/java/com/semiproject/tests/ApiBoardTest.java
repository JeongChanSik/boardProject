package com.semiproject.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.semiproject.controllers.boards.BoardForm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
public class ApiBoardTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("글 작성 성공 시 201 응답")
    void boardSaveTest() throws Exception {

        BoardForm form = new BoardForm();
        form.setBId("notice");
        form.setSubject("제목");
        form.setContent("내용");
        form.setPoster("작성자");
        // 이 값을 JSON 형태로 넘김

        ObjectMapper om = new ObjectMapper();
        String params = om.writeValueAsString(form);

        mockMvc.perform(post("/api/v1/board/write/notice")
                .with(csrf())
                        .contentType("application/json")
                        .characterEncoding("UTF-8")
                        .content(params)
        ).andDo(print()).andExpect(status().isCreated());
    }
}
