package com.semiproject.models.board;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.semiproject.entities.BoardData;
import com.semiproject.entities.QBoardData;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardListService {

    @PersistenceContext // Autowired와 같은 의미
    private EntityManager em;

    public List<BoardData> getList() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QBoardData boardData = QBoardData.boardData;
        JPAQuery<BoardData> query = queryFactory.selectFrom(boardData)
                .leftJoin(boardData.member)
                .fetchJoin();

        List<BoardData> items = query.fetch();

        return items;
    }

}
