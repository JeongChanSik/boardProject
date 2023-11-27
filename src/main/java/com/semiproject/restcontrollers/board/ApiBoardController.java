package com.semiproject.restcontrollers.board;

import com.semiproject.commons.exceptions.BadRequestException;
import com.semiproject.commons.rest.JSONData;
import com.semiproject.controllers.boards.BoardForm;
import com.semiproject.entities.BoardData;
import com.semiproject.models.board.BoardInfoService;
import com.semiproject.models.board.BoardSaveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.FieldError;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
public class ApiBoardController {

    private final BoardSaveService saveService;
    private final BoardInfoService infoService;

    /**
     * 게시글 작성 API
     *
     * @param bId   게시판 ID
     * @param form  게시글 폼 데이터
     * @param errors 검증 에러
     * @return ResponseEntity
     */
    @PostMapping("/write/{bId}")
    public ResponseEntity write(@PathVariable("bId") String bId, @RequestBody @Valid BoardForm form, Errors errors) {

        if(errors.hasErrors()) {
            String message = errors.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage).collect(Collectors.joining(","));
            throw new BadRequestException(message);
        }

        saveService.save(form);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    /**
     * 게시글 조회 API
     *
     * @param seq 게시글 일련번호
     * @return JSONData
     */
    @GetMapping("/view/{seq}")
    public JSONData<BoardData> view(@PathVariable("seq") Long seq) {

        BoardData data = infoService.get(seq);

        return new JSONData<>(data);

    }
}
