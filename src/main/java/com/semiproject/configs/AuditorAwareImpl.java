package com.semiproject.configs;

import com.semiproject.models.member.MemberInfo;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware {
    @Override
    public Optional<String> getCurrentAuditor() {

        String email = null;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();


        if(auth != null && auth.getPrincipal() instanceof MemberInfo) {
            //Object principal = auth.getPrincipal(); // 비회원 - String(문자열) : anonymousUser, 회원 - UserDetails 구현 객체
            MemberInfo member = (MemberInfo) auth.getPrincipal();
            email = member.getEmail();
        }

        return Optional.ofNullable(email);
    }
}
