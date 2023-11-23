package com.semiproject.entities;

import com.semiproject.commons.constants.BoardAuthority;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board extends BaseMember{

    @Id
    @Column(length = 30)
    private String bId;

    @Column(length = 60, nullable = false) // 필수 항목으로 인해 not null 설정
    private String bName; // 게시판 명

    private boolean active; // 사용 여부

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private BoardAuthority authority = BoardAuthority.ALL; // 기본값 설정

    @Lob
    private String category; // 카테고리

}
