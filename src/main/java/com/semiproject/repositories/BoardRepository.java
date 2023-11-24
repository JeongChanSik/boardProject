package com.semiproject.repositories;

import com.semiproject.entities.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
/**
 * 게시판 정보에 대한 JPA Repository 인터페이스입니다.
 */
public interface BoardRepository extends JpaRepository<Board, String>, QuerydslPredicateExecutor<Board> {

}
