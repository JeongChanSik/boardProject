package com.semiproject.entities;

import com.semiproject.commons.constant.MemberType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity /*데이터 하나하나를 의미한다.*/
public class Member {
    @Id /* JPA에서는 @Id를 반드시 입력해줘야 한다.*/
    private Long userNo;

    private String email;

    private String password;

    private String userNm;

    private String mobile;

    private MemberType mtype = MemberType.USER;

    private LocalDateTime regDt;
    private LocalDateTime modDt;
}
