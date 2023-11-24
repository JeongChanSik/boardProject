package com.semiproject.repositories;

import com.semiproject.entities.BoardData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * 게시판 데이터에 대한 JPA Repository 인터페이스입니다.
 */
public interface BoardDataRepository extends JpaRepository<BoardData, Long>, QuerydslPredicateExecutor<BoardData> {
}
