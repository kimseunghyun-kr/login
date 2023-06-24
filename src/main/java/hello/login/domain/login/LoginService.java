package hello.login.domain.login;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository = new MemberRepository();

    public Member login(String loginId , String password){
        return memberRepository.findByLoginId(loginId)
                .filter(x -> x.getPassword().equals(password))
                .orElseGet(()->null);
    }
}
