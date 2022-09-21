package com.ll.exam.fileupload.app.member.controller;

import com.ll.exam.fileupload.app.member.entity.Member;
import com.ll.exam.fileupload.app.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;


    @GetMapping("/join")
    public String showJoin() {
        return "member/join";
    }

    @PostMapping("/join")
    public String Join(String username, String password, String email, MultipartFile profileImg, HttpSession session) { // join form의 내용들을 파라미터로 가져온다.
        Member oldMember = memberService.getMemberByUsername(username);

        if(oldMember != null) {
            return "redirect:/?errorMsg=Already Join User";
        }

        Member member = memberService.join(username, "{noop}" + password, email, profileImg); // {noop}은 패스워드를 암호화 하지 않는다는 뜻

        session.setAttribute("loginedMemberId", member.getId()); // 세션에 member의 id를 저장하여 html로 보내준다.

        return "redirect:/member/profile";
    }

    @GetMapping("/profile")
    public String showProfile(HttpSession session) {
        Long loginedMemberId = (Long)session.getAttribute("loginedMemberId");
        boolean isLogined = loginedMemberId != null;

        if(isLogined == false) {
            return "redirect:/?errorMsg=Need To Login."; // 메인페이지로 이동하면서 추가로 에러메시지를 보내준다.
        }

        return "member/profile";
    }
}
