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

/**
 * Spring Security에서 사용자 정보를 조회하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
public class MemberInfoService implements UserDetailsService {

    private final MemberRepository repository;

    /**
     * 주어진 이메일에 해당하는 사용자 정보를 조회하여 UserDetails 객체로 반환
     *
     * @param username 조회할 사용자의 이메일
     * @return 조회된 사용자 정보
     * @throws UsernameNotFoundException 주어진 이메일에 해당하는 사용자가 없는 경우 발생하는 예외
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 이메일을 사용하여 사용자 정보를 조회하고, 없으면 예외 발생
        Member member = repository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));

        // 사용자의 권한 정보를 생성
        List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(member.getMtype().name()));

        // UserDetails 객체를 생성하여 반환
        return MemberInfo.builder()
                .email(member.getEmail())
                .password(member.getPassword()) // 해쉬가 되어 있는 비밀번호
                .authorities(authorities)
                .member(member)
                .build();
    }
}
