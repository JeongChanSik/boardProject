package com.semiproject.jpaex;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.semiproject.entities.BoardData;
import com.semiproject.entities.QBoardData;
import com.semiproject.repositories.BoardDataRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

@SpringBootTest
@TestPropertySource(properties = "spring.profiles.active=test")
public class Ex05 {
    @Autowired
    private BoardDataRepository repository;

    @Test
    void test1() {
        QBoardData boardData = QBoardData.boardData;
        BooleanExpression cond1 = boardData.subject.contains("목");

        List<BoardData> items = (List<BoardData>)repository.findAll(cond1);
        items.stream().forEach(System.out::println);
    }
}
