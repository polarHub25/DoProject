package kr.hh.liverary.config.auth;

import kr.hh.liverary.domain.user.User;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String provider;
    private String nickname;

    public User toUser(){
        return User.builder()
                .name(name)
                .email(email)
                .provider(provider)
                .build();
    }
}
