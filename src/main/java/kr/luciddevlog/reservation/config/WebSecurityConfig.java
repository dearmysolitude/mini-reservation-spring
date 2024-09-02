package kr.luciddevlog.reservation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**")) // H2 콘솔에 대해 CSRF 보호 비활성화
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)) // H2 콘솔을 위한 X-Frame-Options 설정
                .authorizeHttpRequests(authorizeRequests -> {
                    authorizeRequests.requestMatchers("/h2-console/**").permitAll(); // H2 콘솔 접근 허용
                    // 모든 사용자에게 허용할 특정 게시판 경로
                    authorizeRequests.requestMatchers("/board/review/list", "/board/notice/list", "/board/{id}", "/board/{id}").permitAll();
                    // /board로 시작하는 나머지 모든 경로는 인증된 사용자만 접근 가능
                    authorizeRequests.requestMatchers("/board/**").authenticated();
                    authorizeRequests.requestMatchers("/admin/**").hasRole("ADMIN");
                    authorizeRequests.anyRequest().permitAll();
                })
                .formLogin(formLogin -> formLogin
                        .loginPage("/user/login")
                        .loginProcessingUrl("/user/login")
                        .successHandler(new SavedRequestAwareAuthenticationSuccessHandler())
                        .failureUrl("/user/login?error=true"))
                .logout(logout -> logout
                        .logoutUrl("/user/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}