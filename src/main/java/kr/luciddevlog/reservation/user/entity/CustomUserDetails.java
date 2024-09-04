package kr.luciddevlog.reservation.user.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {
    // Refactor 필요: UserItem 대신 최소한의 데이터만 가지는 DTO로 대체해야: 비밀번호, Username, Role
    private final UserItem userItem;

    public CustomUserDetails(UserItem userItem) {
        this.userItem = userItem;
    }

    @Override
    public String getPassword() {
        return userItem.getPassword();
    }

    @Override
    public String getUsername() {
        return userItem.getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(userItem.getRole().name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // UserItem의 추가 정보에 접근하기 위한 메서드
    public UserItem getUserItem() {
        return userItem;
    }

}
