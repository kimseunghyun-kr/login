package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.session.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    MemberRepository memberRepository = new MemberRepository();
    private final SessionManager sessionManager;
//    @GetMapping("/")
    public String home() {
        return "home";
    }

//    @GetMapping("/")
//    public String homeLogin(@CookieValue(name = "memberId", required = false) Long memberId, Model model){
//        if(memberId == null) {
//            return "home";
//        }
//
//        Member loginMember = memberRepository.findById(memberId);
//        if(loginMember == null){
//            return "home";
//        }
//
//        model.addAttribute("member", loginMember);
//        return "loginHome";
//
//    }


//    @GetMapping("/")
    public String homeLogin(
            @CookieValue(name = "memberId", required = false) Long memberId,
            Model model) {
        if (memberId == null) {
            return "home";
        }
        //로그인
        Member loginMember = memberRepository.findById(memberId);
        if (loginMember == null) {
            return "home";
        }
        model.addAttribute("member", loginMember);
        return "loginHome";
    }

//    @GetMapping("/")
    public String homeLoginV2(HttpServletRequest request, Model model) {
        //세션 관리자에 저장된 회원 정보 조회
        Member member = (Member)sessionManager.getSession(request);
        if (member == null) {
            return "home";
        }
        //로그인
        model.addAttribute("member", member);
        return "loginHome";
    }

    @GetMapping("/")
    public String homeLoginV3(@SessionAttribute(name = "loginMember", required = false) Member loginMember, Model model) {

        if(loginMember == null) {
            return "home";
        }

        model.addAttribute("member", loginMember);
        return "loginHome";
    }
}