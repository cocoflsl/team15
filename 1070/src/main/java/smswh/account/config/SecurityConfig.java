package smswh.account.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/user").hasRole("ADMIN")
                .and() // 추가
                .formLogin() // form 기반 로그인인 경우
                .loginPage("/") // 로그인 필요한 URL 접근시 /로 이동
                .defaultSuccessUrl("/admin")
                .usernameParameter("adminId") // 로그인시 form에서 가져올 값
                .and()
                .logout() // logout할 때
                .logoutSuccessUrl("/") // 로그아웃 성공시 /로 이동
                .invalidateHttpSession(true) // 인증 정보 삭제, 세션 무효화
                .and()
                .exceptionHandling()
                .accessDeniedPage("/accessDenied");


        http.formLogin();
        http.httpBasic();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        String password = passwordEncoder().encode("1234");

        auth.inMemoryAuthentication().withUser("user").password(password).roles("USER");

    }
}
