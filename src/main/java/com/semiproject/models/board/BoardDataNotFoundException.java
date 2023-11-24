package com.semiproject.models.board;

import com.semiproject.commons.exceptions.AlertBackException;
import org.springframework.http.HttpStatus;

public class BoardDataNotFoundException extends AlertBackException {

    public BoardDataNotFoundException() {
        super("등록되지 않은 게시글입니다.");
        setStatus(HttpStatus.NOT_FOUND);
    }
}
