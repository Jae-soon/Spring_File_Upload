package com.ll.exam.fileupload.app.member.service;

import com.ll.exam.fileupload.app.member.entity.Member;
import com.ll.exam.fileupload.app.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {
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
}
