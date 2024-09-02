package kr.luciddevlog.reservation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests -> {
                    // 모든 사용자에게 허용할 특정 게시판 경로
                    authorizeRequests.requestMatchers("/board/review/list", "/board/notice/list", "/board/{id}").permitAll();
                    // /board로 시작하는 나머지 모든 경로는 인증된 사용자만 접근 가능
                    authorizeRequests.requestMatchers("/board/**").authenticated();
                    authorizeRequests.requestMatchers("/admin/**").hasRole("ADMIN");
                    authorizeRequests.anyRequest().permitAll();
                })
                .formLogin(formLogin -> formLogin.loginPage("/login"))
                .logout(logout -> logout.logoutUrl("/logout"))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}