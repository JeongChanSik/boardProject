package com.semiproject.jpaex;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import com.semiproject.commons.constant.MemberType;
import com.semiproject.entities.Member;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class Ex02 {


    @PersistenceContext
    private EntityManager em;

    @Test
    void test1() {
        Member member = Member.builder()
                .email("user01@test.org")
                .password("123456")
                .userNm("사용자01")
                .mobile("010")
                .mtype(MemberType.USER)
                .build();

        em.persist(member);
        em.flush();

        Member member2 = em.find(Member.class, member.getUserNo());
        System.out.println("수정 전" + member2);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);

        }

        member2.setUserNm("(수정)사용자01");
        em.flush();
        member2 = em.find(Member.class, member.getUserNo());
        System.out.println("(수정 후)" + member2);
    }

    @Test
    void test2() {

    }
}
