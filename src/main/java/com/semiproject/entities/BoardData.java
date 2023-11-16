package com.semiproject.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
@NoArgsConstructor @AllArgsConstructor
public class BoardData extends Base{

    @Id
    @GeneratedValue
    private Long seq;

    @Column(length = 100, nullable = false)
    private String subject;

    @Lob // 라지Object CharacterLargeObject
    @Column(nullable = false)
    private String content;




}
