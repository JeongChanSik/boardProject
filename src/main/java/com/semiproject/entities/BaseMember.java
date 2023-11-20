package com.semiproject.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass @Getter @Setter
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseMember extends Base{

    @CreatedBy
    @Column(length = 65, updatable = false)
    private String createdBy; // 생성한 사람

    @LastModifiedBy
    @Column(length = 65, insertable = false)
    private String modifiedBy; // 수정한 사람

}
