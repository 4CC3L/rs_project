package com.social.security.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.social.security.model.dto.CredentialsDTO;
import com.social.security.model.dto.UserDTO;
import com.social.security.model.dto.UserInfoDTO;
import com.social.security.service.SecurityService;
import com.social.security.service.UserService;

import reactor.core.publisher.Mono;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final UserService userService;

    

    public SecurityServiceImpl(UserService userService) {
        this.userService = userService;
    }

    public Mono<UserInfoDTO> signUp(UserDTO user) {
        return userService.createUser(user);
    }

    public Mono<Map<String, Object>> signIn(CredentialsDTO credentials) {
        return null;
    } 
    
}
