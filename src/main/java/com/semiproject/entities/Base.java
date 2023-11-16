package com.semiproject.entities;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter @Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Base {
    @Column(updatable = false)
    @CreatedDate
    //@CreationTimestamp : insert 쿼리할 때 현재 날짜 시간이 주입됨
    private LocalDateTime createdAt;

    @Column(insertable = false)
    @LastModifiedDate
    //@UpdateTimestamp : update 쿼리할 때 현재 날짜 시간이 주입됨
    private LocalDateTime modifiedAt;
}
