package smswh.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import smswh.domain.Member;
import smswh.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;

    public Member login(String loginId, String password) {
        Member findMember = memberRepository.findByLoginId(loginId);
        if(findMember != null){
            if(findMember.getPassword().equals(password))   return findMember;
        }
        return null;
    }
}
