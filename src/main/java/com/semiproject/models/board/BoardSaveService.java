package com.semiproject.models.board;

import com.semiproject.controllers.boards.BoardForm;
import com.semiproject.entities.BoardData;
import com.semiproject.repositories.BoardDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 게시글을 저장하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
public class BoardSaveService {

    private final BoardDataRepository boardDataRepository; // 게시글 데이터 리포지토리 의존성 주입

    /**
     * 주어진 게시글 폼 정보를 이용하여 게시글을 저장
     *
     * @param form 게시글 폼 정보
     */
    public void save(BoardForm form) {
        Long seq = form.getSeq();
        String mode = Objects.requireNonNullElse(form.getMode(), "add"); // 안전하게 사용하기 위해 Objects를 사용한다.

        BoardData data = null;
        if (mode.equals("update") && seq != null) {
            // 수정 모드이고 seq가 주어진 경우 해당 게시글을 조회하고, 없으면 예외 발생
            data = boardDataRepository.findById(seq).orElseThrow(BoardDataNotFoundException::new);
        } else {
            // 신규 등록 모드인 경우 새로운 게시글 객체 생성
            data = new BoardData();
        }

        // 게시글 정보 설정
        data.setSubject(form.getSubject());
        data.setContent(form.getContent());
        data.setPoster(form.getPoster());

        // 게시글 저장
        boardDataRepository.saveAndFlush(data);
    }
}
