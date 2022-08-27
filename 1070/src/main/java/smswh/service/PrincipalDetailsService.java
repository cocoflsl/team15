package smswh.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import smswh.account.config.PrincipalDetails;
import smswh.domain.Member;
import smswh.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 로그인한 member가 db에 저장되어 있는지 확인
        Member findMember = memberRepository.findByName(username);

        // db에 저장되어 있다면 해당 member를 권한 관리 대상으로 분류
        if(findMember != null) {
            return new PrincipalDetails(findMember);
        }
        return null;
    }
}
