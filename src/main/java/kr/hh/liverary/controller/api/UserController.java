package kr.hh.liverary.controller.api;

import kr.hh.liverary.config.auth.MemberProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final HttpSession httpSession;

    @GetMapping("/api/user")
    public MemberProfile getUserName(){
        MemberProfile user = (MemberProfile) httpSession.getAttribute("user");

        if(user == null) throw new UsernameNotFoundException("User is not found!");

        return user;
    }
}
