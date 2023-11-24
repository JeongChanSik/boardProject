package com.semiproject.configs;

import com.semiproject.models.member.MemberInfo;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * JPA Auditing에서 현재 사용자를 제공하는 구현체
 */
@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    /**
     * 현재 인증된 사용자의 이메일을 반환
     *
     * @return 현재 사용자의 이메일
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        String email = null;

        // 현재 인증 정보를 가져옴
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // 인증 정보가 존재하고, Principal이 MemberInfo 객체인 경우 이메일을 추출
        if (auth != null && auth.getPrincipal() instanceof MemberInfo) {
            MemberInfo member = (MemberInfo) auth.getPrincipal();
            email = member.getEmail();
        }

        // Optional로 감싸서 반환
        return Optional.ofNullable(email);
    }
}
