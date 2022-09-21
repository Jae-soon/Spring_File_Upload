package com.ll.exam.fileupload.app.member.controller;

import com.ll.exam.fileupload.app.member.entity.Member;
import com.ll.exam.fileupload.app.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;


    @GetMapping("/join")
    public String showJoin() {
        return "member/join";
    }

    @PostMapping("/join")
    public String Join(HttpServletRequest req, String username, String password, String email, MultipartFile profileImg) { // join form의 내용들을 파라미터로 가져온다.
        Member oldMember = memberService.getMemberByUsername(username);

        String passwordClearText = password; // 평문 암호
        password = passwordEncoder.encode(password); // 암호화처리

        if(oldMember != null) {
            return "redirect:/?errorMsg=Already Join User";
        }

        Member member = memberService.join(username, password, email, profileImg); // {noop}은 패스워드를 암호화 하지 않는다는 뜻

        try {
            req.login(username, passwordClearText); // password Encoder가 없으면 자격증명이 되지 않는다. -> 평문 암호를 사용해야한다.
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }

        return "redirect:/member/profile";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public String showProfile(Principal principal, Model model) { // Principal은 시큐리티로 얻은 정보들 의미
        Member loginedMember = memberService.getMemberByUsername(principal.getName());
        model.addAttribute("loginedMember", loginedMember);

        return "member/profile";
    }
}
