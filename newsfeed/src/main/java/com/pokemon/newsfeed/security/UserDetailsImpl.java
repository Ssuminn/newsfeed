package com.pokemon.newsfeed.security;

import com.pokemon.newsfeed.entity.User;
import com.pokemon.newsfeed.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {
    // UserDetails 인터페이스는 Spring Security에서 사용자의 정보를 나타내는데 사용됩니다.

    private final User user;
    // User 객체 생성 후 생성자를 통해 이 객체를 받아서 멤버 변수로 사용
    public User getUser() {
        return user;
    }
    @Override
    public String getUsername() {
        return user.getUserId();
    }
    // UserDetails 메서드 오바라이드하여 사용자 이름 반환
    // todo: 이 부분 id로 바꾸는 방법

    @Override
    public String getPassword() {
        return user.getPassword();
    }
    // UserDetails 메서드 오버라이드하여 사용자 비밀번호 반환

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        UserRoleEnum role = user.getRole();
        String authority = role.getAuthority();

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);
        return authorities;
    }
    // 사용자 권한 반환하는 메서드 나중에 권한 설정도 생기면 추가

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    // 사용자 계정 만료

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    // 사용자 계정 잠금

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    // 사용자 자격 증명 만료

    @Override
    public boolean isEnabled() {
        return false;
    }
    // 사용자 사용 가능 여뷰
}