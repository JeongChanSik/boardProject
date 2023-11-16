package com.semiproject.models.member;

import com.semiproject.entities.Member;
import com.semiproject.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberInfoService implements UserDetailsService {

    private final MemberRepository repository;

    // 필수 설정 값
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = repository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));

        List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(member.getMtype().name()));

        return MemberInfo.builder()
                .email(member.getEmail())
                .password(member.getPassword()) // 해쉬가 되어 있는 비번
                .authorities(authorities)
                .member(member)
                .build();
    }
}
