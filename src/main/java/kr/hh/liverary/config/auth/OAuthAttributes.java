package kr.hh.liverary.config.auth;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

public enum OAuthAttributes {
    GOOGLE("google" , (attributes) -> {
        SessionUser sessionUser = new SessionUser();
        sessionUser.setName((String) attributes.get("name"));
        sessionUser.setEmail((String) attributes.get("email"));
        return sessionUser;
    });

    private final String registrationId;
    private final Function<Map<String, Object>, SessionUser> of;

    OAuthAttributes(String registrationId , Function<Map<String, Object>, SessionUser> of){
        this.registrationId = registrationId;
        this.of = of;
    }

    public static SessionUser extract(String registrationId, Map<String, Object> attributes){
        return Arrays.stream(values())
                .filter(provider -> registrationId.equals(provider.registrationId))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .of.apply(attributes);
    }
}
