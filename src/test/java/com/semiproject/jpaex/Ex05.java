package com.semiproject.jpaex;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.semiproject.entities.BoardData;
import com.semiproject.entities.QBoardData;
import com.semiproject.repositories.BoardDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@TestPropertySource(properties = "spring.profiles.active=test")
public class Ex05 {
    @Autowired
    private BoardDataRepository repository;

    @BeforeEach
    void init() {
        List<BoardData> items = new ArrayList<>();
        for(int i = 1; i <= 10; i++) {
            BoardData item = BoardData.builder()
                    .subject("제목" + i)
                    .content("내용" + i)
                    .build();
            items.add(item);
        }
        repository.saveAllAndFlush(items);
    }

    @Test
    void test1() {
        QBoardData boardData = QBoardData.boardData;
        // BooleanExpression cond1 = boardData.subject.contains("목");

        List<BoardData> items = (List<BoardData>)repository.findAll(boardData.subject.contains("목"));
        items.stream().forEach(System.out::println);

        repository.count();
    }

    @Test
    void test2() {

        QBoardData boardData = QBoardData.boardData;
        BooleanBuilder andBuilder = new BooleanBuilder();
        BooleanBuilder orBuilder = new BooleanBuilder();
        orBuilder.or(boardData.subject.contains("목"))
                .or(boardData.content.contains("용"));
        andBuilder.and(orBuilder);

        andBuilder.and(orBuilder)
                .and(boardData.seq.in(2, 4, 6, 8));

        /*builder.and(boardData.subject.contains("목"))
                .and(boardData.content.contains("용"))
                .and(boardData.seq.in(2, 4, 6, 8));*/

        List<BoardData> items = (List<BoardData>)repository.findAll(andBuilder);
        items.stream().forEach(System.out::println);
    }

}