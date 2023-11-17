package com.semiproject.jpaex;

import com.semiproject.commons.constant.MemberType;
import com.semiproject.entities.BoardData;
import com.semiproject.entities.Member;
import com.semiproject.repositories.BoardDataRepository;
import com.semiproject.repositories.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
// @TestPropertySource("spring.profiles.active=test")
public class Ex06 {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BoardDataRepository boardDataRepository;

    @BeforeEach
    void init() {
        Member member = Member.builder()
                .email("user01@test.org")
                .password("123456")
                .userNm("사용자01")
                .mtype(MemberType.USER)
                .build();

        memberRepository.saveAndFlush(member);

        List<BoardData> items = new ArrayList<>();

        for(int i = 1; i <= 10; i++) {
            BoardData item = BoardData.builder()
                    .subject("제목" + i)
                    .content("내용" + i)
                    .member(member)
                    .build();
            items.add(item);
        }

        boardDataRepository.saveAllAndFlush(items);

    }

    @Test
    void test1() {
        BoardData data = boardDataRepository.findById(1L).orElse(null);
        Member member = data.getMember();
        System.out.println(member);
    }

}
