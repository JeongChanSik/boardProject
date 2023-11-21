package com.semiproject.models.member;

import com.semiproject.commons.constants.MemberType;
import com.semiproject.controllers.members.JoinValidator;
import com.semiproject.controllers.members.RequestJoin;
import com.semiproject.entities.Member;
import com.semiproject.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Service
@RequiredArgsConstructor
public class MemberSaveService {
    private final MemberRepository repository;
    private final PasswordEncoder encoder; // 스프링 시큐리티가 제공하는 기능
    private final JoinValidator validator;

    /**
     * 회원가입 처리
     * @param form
     * @param errors
     */
    public Member join(RequestJoin form, Errors errors) {
        validator.validate(form, errors);

        if (errors.hasErrors()) { // 여기까지 들어오면 검증에 실패했다는 뜻이다.
            return null;
        }

        String hash = encoder.encode(form.getPassword());

        Member member = Member.builder()
                .email(form.getEmail())
                .userNm(form.getUserNm())
                .mobile(form.getMobile())
                .mtype(MemberType.USER)
                .password(hash)
                .build();

        save(member);

        member = repository.findByEmail(form.getEmail()).get();
        return member;
    }

    // 따로 분리한 이유 : 회원가입 시와 회원 정보 수정 시 구별하기 위해
    public void save(Member member) {
        String mobile = member.getMobile();
        if (mobile != null) {
            mobile = mobile.replaceAll("\\D", "");
            member.setMobile(mobile);
        }

        repository.saveAndFlush(member);
    }

}