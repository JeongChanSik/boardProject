package com.semiproject.models.board.config;

import com.semiproject.commons.Utils;
import com.semiproject.commons.exceptions.AlertBackException;
import org.springframework.http.HttpStatus;

public class BoardNotFoundException extends AlertBackException {
    public BoardNotFoundException() {
        super(Utils.getMessage("NotFound.board", "error")); // 게시판이 없다라고 알려줌
        setStatus(HttpStatus.NOT_FOUND);
    }
}
