package kr.hh.liverary.config.auth;


import kr.hh.liverary.domain.user.User;
import kr.hh.liverary.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/*
  OAuth2 로그인 성공시 db에 저장하는 작업
*/
@Service
@RequiredArgsConstructor
public class OAuthService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest); //OAuth 서비스(구글)에서 가져온 유저 정보를 담고있음

        String registrationId = userRequest.getClientRegistration()
                                            .getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                                                    .getProviderDetails()
                                                    .getUserInfoEndpoint()
                                                    .getUserNameAttributeName(); //OAuth 로그인시 키(pk)가 되는 값
        Map<String, Object> attributes = oAuth2User.getAttributes(); //OAuth 서비스의 유저 정보들
        SessionUser sessionUser = OAuthAttributes.extract(registrationId, attributes); //registrationId에 따라 유저 정보를 통해 공통된 UserProfile 객체로 만들어줌.
        sessionUser.setProvider(registrationId);
        User user = saveOrUpdate(sessionUser);

        Map<String, Object> customAttribute = customAttribute(attributes, userNameAttributeName, sessionUser, registrationId);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("USER")),
                customAttribute,
                userNameAttributeName);
    }

    private Map customAttribute(Map attributes, String userNameAttributeName, SessionUser sessionUser , String registrationId){
        Map<String, Object> customAttribute  = new LinkedHashMap<>();
        customAttribute.put(userNameAttributeName, attributes.get(userNameAttributeName));
        customAttribute.put("provider", registrationId);
        customAttribute.put("name", sessionUser.getName());
        customAttribute.put("email" , sessionUser.getEmail());
        return customAttribute;
    }

    private User saveOrUpdate(SessionUser sessionUser){
        User user = userRepository.findByEmailAndProvider(sessionUser.getEmail() , sessionUser.getProvider())
                .map(m->m.update(sessionUser.getName(), sessionUser.getEmail()))
                .orElse(sessionUser.toUser());
        return userRepository.save(user);
    }
}
