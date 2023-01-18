package kr.hh.liverary.config.auth;


import kr.hh.liverary.domain.user.Member;
import kr.hh.liverary.domain.user.MemberRepository;
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

    private final MemberRepository memberRepository;

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
        MemberProfile memberProfile = OAuthAttributes.extract(registrationId, attributes); //registrationId에 따라 유저 정보를 통해 공통된 UserProfile 객체로 만들어줌.
        memberProfile.setProvider(registrationId);
        Member member  = saveOrUpdate(memberProfile);

        Map<String, Object> customAttribute = customAttribute(attributes, userNameAttributeName, memberProfile, registrationId);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("USER")),
                customAttribute,
                userNameAttributeName);
    }

    private Map customAttribute(Map attributes, String userNameAttributeName, MemberProfile memberProfile, String registrationId){
        Map<String, Object> customAttribute  = new LinkedHashMap<>();
        customAttribute.put(userNameAttributeName, attributes.get(userNameAttributeName));
        customAttribute.put("provider", registrationId);
        customAttribute.put("name", memberProfile.getName());
        customAttribute.put("email" , memberProfile.getEmail());
        return customAttribute;
    }

    private Member saveOrUpdate(MemberProfile memberProfile){
        Member member  = memberRepository.findByEmailAndProvider(memberProfile.getEmail() , memberProfile.getProvider())
                .map(m->m.update(memberProfile.getName(), memberProfile.getEmail()))
                .orElse(memberProfile.toMember());
        return memberRepository.save(member);
    }
}
