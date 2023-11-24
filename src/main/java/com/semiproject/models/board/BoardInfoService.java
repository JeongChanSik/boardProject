package com.semiproject.models.board;

import com.semiproject.entities.BoardData;
import com.semiproject.repositories.BoardDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardInfoService {

    private final BoardDataRepository boardDataRepository; // 의존성 주입

    public BoardData get(Long seq) {

        BoardData data = boardDataRepository.findById(seq).orElseThrow(BoardDataNotFoundException::new);

        return data;
    }
}
