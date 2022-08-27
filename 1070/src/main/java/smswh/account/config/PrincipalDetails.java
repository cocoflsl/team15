package smswh.account.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import smswh.domain.Member;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@ToString
@RequiredArgsConstructor
// form 로그인에는 UserDetails가 사용된다.
public class PrincipalDetails implements UserDetails {

    private final Member member;

    @Override
    // <? extends T> : T 타입과 T 타입을 상속받는 자식 클래스 타입만 타입 변수로 사용 가능.
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add((GrantedAuthority) () -> member.getRole().toString());
        return collect;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false; // 계정 만료 여부
    }

    @Override
    public boolean isAccountNonLocked() {
        return false; // 계정 잠김 여부
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false; // 계정 비밀번호 만료 여부
    }

    @Override
    public boolean isEnabled() {
        return false; // 계정 활성화 여부
    }
}
