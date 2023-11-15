package com.semiproject.jpaex;

import com.semiproject.commons.constant.MemberType;
import com.semiproject.entities.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
// @TestPropertySource(activeProfiles="test")
public class Ex01 {

    @PersistenceContext
    private EntityManager em;

    @BeforeEach
    void init() {
        Member member = new Member();
        // member.setUserNo(1L);
        member.setEmail("user01@test.org");
        member.setUserNm("사용자01");
        member.setPassword("_aA123456");
        member.setMobile("01012341234");
        member.setMtype(MemberType.USER);

        em.persist(member); /* INSERT 쿼리 변화 감지 상태 */
        em.flush(); /* 커밋 */
        em.clear(); // 영속성 비우기
    }

    @Test
    void test2() {
        Member member = em.find(Member.class, 1L); // DB -> 영속성 컨텍스트로 추가
        System.out.println(member);

        Member member2 = em.find(Member.class, 1L); // 영속성 컨텍스트에서 조회한다.
        System.out.println(member2);

        TypedQuery<Member> query = em.createQuery("SELECT m FROM Users AS m WHERE m.email LIKE :key", Member.class); // m 은 *과 같다.
        query.setParameter("key", "%user%");

        Member member3 = query.getSingleResult();
        member3.setUserNm("(수정)사용자01");
        em.flush();
        System.out.println(member3);
    }

    @Test
    void test1() {
        Member member = new Member();
        // member.setUserNo(1L);
        member.setEmail("user01@test.org");
        member.setUserNm("사용자01");
        member.setPassword("_aA123456");
        member.setMobile("01012341234");
        member.setMtype(MemberType.USER);

        em.persist(member); /* INSERT 쿼리 변화 감지 상태 */
        em.flush(); /* 커밋 */

        member.setUserNm("(수정)사용자01"); /* UPDATE 쿼리 영속성 분리, 변화 감지 하지 않음 */
        em.flush(); /* 커밋 */

        em.merge(member); // 분리된 영속 상태 -> 영속 상태, 변화 감지 함
        em.flush();

        /* em.remove(member);
        em.flush(); */
    }

    @Test
    void test3() {

    }
}
