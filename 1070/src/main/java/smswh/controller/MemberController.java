package smswh.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import smswh.account.config.Role;
import smswh.domain.Member;
import smswh.repository.MemberRepository;

@Controller
@RequiredArgsConstructor
@Transactional
public class MemberController {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/signupForm")
    public String joinForm() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signUp(@ModelAttribute Member member) { // @ModelAttribute : 사용자가 전달한 값을 객체 형태로 매핑
        member.setRole(Role.USER);

        String encodePwd = bCryptPasswordEncoder.encode(member.getPassword());
        member.setPassword(encodePwd);

        memberRepository.save(member);
        return "/loginForm";
    }

    @GetMapping("/admin")
    @ResponseBody
    public String admin() {
        return "admin";
    }



    @GetMapping("/loginForm")
    public String loginForm() {
        return "login";
    }

}
