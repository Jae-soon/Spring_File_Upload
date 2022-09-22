package com.ll.exam.fileupload.app.base;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Profile(value = {"dev", "test"}) // 시큐리티 설정을 개발용과 테스트용으로 사용
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // csrf 해제
                .authorizeRequests()
                .antMatchers("/**")
                .permitAll() // 로그인을 하지 않아도 모든페이지 허용
                .and()
                .formLogin()
                .loginPage("/member/login")
                .loginProcessingUrl("/member/login");
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() { // password encoder 객체 주입
        return new BCryptPasswordEncoder();
    }
}