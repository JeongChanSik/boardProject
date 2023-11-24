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

    @PostMapping("/write/{bId}") // bId = @PathVariable bId 때문에 필요한 어노테이션
    public ResponseEntity write(@PathVariable("bId") String bId, @RequestBody @Valid BoardForm form, Errors errors) {

        if(errors.hasErrors()) {
            String message = errors.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage).collect(Collectors.joining(","));
            throw new BadRequestException(message);
        }

        saveService.save(form);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @GetMapping("/view/{seq}")
    public JSONData<BoardData> view(@PathVariable("seq") Long seq) {

        BoardData data = infoService.get(seq);

        return new JSONData<>(data);

    }
}
