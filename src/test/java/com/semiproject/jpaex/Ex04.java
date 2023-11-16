package com.semiproject.jpaex;

import com.semiproject.entities.BoardData;
import com.semiproject.repositories.BoardDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.domain.Sort.Order.asc;
import static org.springframework.data.domain.Sort.Order.desc;

@SpringBootTest
@TestPropertySource(properties = "spring.profiles.active=test")
public class Ex04 {

    @Autowired
    private BoardDataRepository repository;

    @BeforeEach
    void init() {
        List<BoardData> items = new ArrayList<>();

        for(int i = 1; i <= 10; i++) {
            items.add(BoardData.builder()
                    .subject("제목" + i)
                    .content("내용" + i)
                    .build());
        }

        repository.saveAllAndFlush(items);
    }

    @Test
    void test1() {
        List<BoardData> items = repository.findAll(Sort.by(desc("seq"), asc("subject")));

        items.stream().forEach(System.out::println);

        repository.findAll();
    }

    @Test
    void test2() {
        // pageNumber : 몇 페이지인지? pageSize : 페이지 안에 몇 개가 들어갈건지? Number는 0부터 Size는 1부터 시작한다.
        Pageable pageable = PageRequest.of(0, 3);
        Page<BoardData> data = repository.findAll(pageable);
        List<BoardData> items = data.getContent();
        items.stream().forEach(System.out::println);
    }
}