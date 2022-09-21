package com.ll.exam.fileupload.app.member.service;

import com.ll.exam.fileupload.app.member.entity.Member;
import com.ll.exam.fileupload.app.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService { // 스프링 시큐리티를 사용한 로그인 사용 시 UserDetailsService를 implement 해주어야 한다.
    @Value("${custom.genFileDirPath}") // 설정파일에서 원하는 값을 가져와 사용할 수 있는 어노테이션
    private String genFileDirPath;

    private final MemberRepository memberRepository;

    public Member getMemberByUsername(String username) {
        return memberRepository.findByUsername(username).orElse(null); // Optional로 넣어줄 경우 Member객체를 return 하거나 null을 return한다.
    }

    public Member join(String username, String password, String email, MultipartFile profileImg) {
        // 상대경로 -> 여러 종류의 사진을 하나의 폴더에 넣지 않기 위해
        String profileImgRelPath = "member/" + UUID.randomUUID().toString() + ".png";
        File profileImgFile = new File(genFileDirPath + "/" + profileImgRelPath); // D:/temp/genFileDir/member/UUID.png

        profileImgFile.mkdirs(); // 관련 폴더가 존재하지 않으면 폴더 생성

        try {
            profileImg.transferTo(profileImgFile); // 파일을 미리 지정한 위치에 저장(File 객체)
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Member member = Member.builder()
                .username(username)
                .email(email)
                .password(password)
                .profileImg(profileImgRelPath) // 이미지를 직접 저장이 아닌  경로 저장 -> 경로에서 꺼내온다.
                .build();

        memberRepository.save(member);
        return member;
    }

    public Member getMemberById(Long loginedMemberId) {
        return memberRepository.findById(loginedMemberId).orElse(null);
    }

    // 스프링 시큐리티를 사용하면 정보를 받는 곳
    // 특정 유저명으로부터 비밀번호 혹은 권한을 알아야 할 때 사용한다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username).get();

        List<GrantedAuthority> authorities = new ArrayList<>(); // 권한들
        authorities.add(new SimpleGrantedAuthority("member")); // member권한 넣기

        return new User(member.getUsername(), member.getPassword(), authorities); // 해당 유저에 권한까지 추가
    }
}
