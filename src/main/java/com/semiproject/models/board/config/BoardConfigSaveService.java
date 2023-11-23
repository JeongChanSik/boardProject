package com.semiproject.models.board.config;

import com.semiproject.commons.constants.BoardAuthority;
import com.semiproject.controllers.admins.BoardConfigForm;
import com.semiproject.entities.Board;
import com.semiproject.repositories.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class BoardConfigSaveService {

    private final BoardRepository boardRepository;

    public void save(BoardConfigForm form) {

        String bId = form.getBId();
        String mode = form.getMode();
        Board board = null;
        if(mode.equals("edit") && StringUtils.hasText(bId)) {
            board = boardRepository.findById(bId).orElseThrow(BoardNotFoundException::new);
        } else {
            board = new Board(); // 새로운 Board 객체 생성
            board.setBId(bId); // Board 객체에 bId값 저장
        }

        board.setActive(form.isActive());
        board.setAuthority(BoardAuthority.valueOf(form.getAuthority()));
        board.setCategory(form.getCategory());

        boardRepository.saveAndFlush(board);

        /*if(bId != null && !bId.isBlank()){
            이렇게도 사용이 가능하다.
        }*/

    }

}
