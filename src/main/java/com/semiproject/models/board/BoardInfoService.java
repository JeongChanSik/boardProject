package com.semiproject.models.board;

import com.semiproject.entities.BoardData;
import com.semiproject.repositories.BoardDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 게시글 정보를 조회하는 서비스 클래스입니다.
 */
@Service
@RequiredArgsConstructor
public class BoardInfoService {

    private final BoardDataRepository boardDataRepository; // 게시글 데이터 리포지토리 의존성 주입

    /**
     * 주어진 게시글 번호에 해당하는 게시글 정보를 조회합니다.
     *
     * @param seq 게시글 번호
     * @return 게시글 정보
     * @throws BoardDataNotFoundException 게시글이 존재하지 않는 경우 발생하는 예외
     */
    public BoardData get(Long seq) {

        // 게시글 번호로 게시글 정보 조회, 존재하지 않으면 예외 발생
        BoardData data = boardDataRepository.findById(seq).orElseThrow(BoardDataNotFoundException::new);

        return data;
    }
}
