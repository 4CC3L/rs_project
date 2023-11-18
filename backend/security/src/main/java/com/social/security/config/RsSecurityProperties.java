package com.social.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class RsSecurityProperties {

    @Value("${rs_security.messages.user-not-found}")
    public String userNotFoundMessage;

    @Value("${rs_security.messages.username-not-found}")
    public String usernameNotFoundMessage;

}
