package com.semiproject.models.board.config;

import com.querydsl.core.BooleanBuilder;
import com.semiproject.commons.ListData;
import com.semiproject.commons.Pagination;
import com.semiproject.commons.Utils;
import com.semiproject.controllers.admins.BoardSearch;
import com.semiproject.entities.Board;
import com.semiproject.repositories.BoardRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
public class BoardConfigInfoService {

    private final BoardRepository repository;
    private final HttpServletRequest request;

    public Board get(String bId) {

        Board data = repository.findById(bId).orElseThrow(BoardNotFoundException::new);

        return data;
    }

    public ListData<Board> getList(BoardSearch search) {

        BooleanBuilder andBuilder = new BooleanBuilder();

        int page = Utils.getNumber(search.getPage(), 1);
        int limit = Utils.getNumber(search.getLimit(), 20);

        // Sort.Order.desc("엔티티 속성명"), Sort.Order.asc("엔티티 속성명") / Sort.by() : Order에 속한 정적 메소드
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(desc("createdAt")));

        Page<Board> data =  repository.findAll(andBuilder, pageable);

        Pagination pagination = new Pagination(page, (int)data.getTotalElements(), 10, limit, request);

        ListData<Board> listData = new ListData<>();
        listData.setContent(data.getContent());
        listData.setPagination(pagination);

        return listData;
    }

}
