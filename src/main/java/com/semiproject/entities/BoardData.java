package com.semiproject.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor @AllArgsConstructor
public class BoardData extends BaseMember{

    @Id
    @GeneratedValue
    private Long seq;

    @Column(length = 100, nullable = false)
    private String subject;

    @Lob // 라지 Object CharacterLargeObject
    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY) // FetchType.LAZY : 지연 로딩 필요할 때만 쿼리를 실행한다.
    @JoinColumn(name = "userNo")
    private Member member;

}
