package com.CreatorConnect.server.auth.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomAuthorityUtils {
    // application.yml 파일에 있는 mail.address.admin 값을 주입받음
    @Value("${mail.address.admin}")
    private String adminMailAddress;

    // ROLE_ADMIN, ROLE_USER 권한을 가진 GrantedAuthority 리스트
    private final List<GrantedAuthority> ADMIN_ROLES = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");
    // ROLE_USER 권한을 가진 GrantedAuthority 리스트
    private final List<GrantedAuthority> USER_ROLES = AuthorityUtils.createAuthorityList("ROLE_USER");
    // ADMIN, USER 문자열 리스트
    private final List<String> ADMIN_ROLES_STRING = List.of("ADMIN", "USER");
    // USER 문자열 리스트
    private final List<String> USER_ROLES_STRING = List.of("USER");

    // 메모리 상의 Role 을 기반으로 권한 정보 생성하는 메소드
    public List<GrantedAuthority> createAuthorities(String email) {
        // email 이 adminMailAddress 와 같은 경우, ADMIN_ROLES 반환. 그렇지 않으면 USER_ROLES 반환
        if (email.equals(adminMailAddress)) {
            return ADMIN_ROLES;
        }
        return USER_ROLES;
    }

    // DB에 저장된 Role 을 기반으로 권한 정보 생성하는 메소드
    public List<GrantedAuthority> createAuthorities(List<String> roles) {
        // roles 리스트를 SimpleGrantedAuthority 리스트로 변환
        List<GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
        return authorities;
    }

    // DB 저장 용도로 roles 문자열 리스트 생성하는 메소드
    public List<String> createRoles(String email) {
        // email 이 adminMailAddress 와 같은 경우, ADMIN_ROLES_STRING 반환. 그렇지 않으면 USER_ROLES_STRING 반환
        if (email.equals(adminMailAddress)) {
            return ADMIN_ROLES_STRING;
        }
        return USER_ROLES_STRING;
    }
}
