package com.semiproject.entities;

import com.semiproject.commons.constant.MemberType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Entity /* 데이터 하나하나를 의미한다. */
@NoArgsConstructor @AllArgsConstructor
@Table(indexes = {
        @Index(name = "idx_member_userNm", columnList = "userNm"),
        @Index(name = "idx_member_mobile", columnList = "mobile")
})
public class Member extends Base{
    /* JPA에서는 @Id를 반드시 입력해줘야 한다. */
    @Id @GeneratedValue(strategy = GenerationType.AUTO) // (strategy = GenerationType.AUTO) 기본값
    private Long userNo;

    @Column(length = 65, unique = true, nullable = false) // 유니크 설정, false가 기본값이다. nullable false 할 경우 not null
    private String email;

    @Column(length = 65, name = "pw", nullable = false) // nullable false로 할 경우 not null
    private String password;

    @Column(length = 40, nullable = false) // nullable false로 할 경우 not null
    private String userNm;

    @Column(length = 11)
    private String mobile;

    @Column(length = 10, nullable = false) // nullable false로 할 경우 not null
    @Enumerated(EnumType.STRING)
    private MemberType mtype = MemberType.USER;

    @ToString.Exclude
    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER) // mappedBy : 주인 명시해주는 역할
    private List<BoardData> items = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "profile_seq")
    private MemberProfile profile;


    /* 거의 안 씀
    @Temporal(TemporalType.DATE)
    private Date date;
    */
}
