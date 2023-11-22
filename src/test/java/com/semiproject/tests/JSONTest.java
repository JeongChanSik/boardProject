package com.semiproject.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.semiproject.entities.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class JSONTest {

    private ObjectMapper om;

    @BeforeEach
    void init() {
        om = new ObjectMapper();
    }
    @Test
    void test1() throws JsonProcessingException {
        Member member = Member.builder()
                .email("user01@test.org")
                .password("12345678")
                .userNm("사용자01")
                .build();
        member.setCreatedAt(LocalDateTime.now());

        String json = om.writeValueAsString(member);
        System.out.println(json);
    }
}
