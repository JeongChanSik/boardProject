package com.semiproject.models.board;

import com.semiproject.controllers.boards.BoardForm;
import com.semiproject.entities.BoardData;
import com.semiproject.repositories.BoardDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BoardSaveService {

    private final BoardDataRepository boardDataRepository; // 의존성 주입

    public void save(BoardForm form) { // BoardForm 커맨드 객체 매개변수로 저장
        Long seq = form.getSeq();
        String mode = Objects.requireNonNullElse(form.getMode(), "add"); // 안전하게 사용하기 위해 Objects를 사용한다.

        BoardData data = null;
        if (mode.equals("update") && seq != null) {
            data = boardDataRepository.findById(seq).orElseThrow(BoardDataNotFoundException::new); // seq를 가져오고 없으면 던져버린다.
        } else {
            data = new BoardData();
        }

        data.setSubject(form.getSubject());
        data.setContent(form.getContent());
        data.setPoster(form.getPoster());

        boardDataRepository.saveAndFlush(data);

    }
}
