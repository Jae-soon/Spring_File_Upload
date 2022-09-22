package com.ll.exam.fileupload.app.base;

import com.ll.exam.fileupload.app.member.service.MemberService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("test")
public class TestInitData {
    // CommandLineRunner는 주로 앱 실행 직후 초기데이터 세팅 및 초기화에 사용된다. -> 테스트 데이터 생성
    @Bean
    CommandLineRunner init(MemberService memberService, PasswordEncoder passwordEncoder) {
        return args -> {
            String password = passwordEncoder.encode("1234");
            memberService.join("user1", password, "user1@test.com");
            memberService.join("user2", password, "user2@test.com");
            memberService.join("user3", password, "user3@test.com");
            memberService.join("user4", password, "user4@test.com");
            memberService.join("user5", password, "user5@test.com");
        };
    }
}
