package com.example.yourssu;
//자동 로그인 페이지 억제
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 보호 비활성화 (개발용, 필요 시 활성화 가능)
                .csrf(csrf -> csrf.disable())

                // 요청 경로별 권한 설정
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/","/register", "/api/users/register", "/**").permitAll()  // 인증 없이 접근 허용
                        .anyRequest().authenticated()  // 나머지 요청은 인증 필요
                )

                // 로그인 폼 비활성화 (기본 로그인 페이지 사용 방지)
                .formLogin(form -> form.disable());

        return http.build();
    }

    // 비밀번호 암호화를 위한 BCryptPasswordEncoder를 빈으로 등록
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}