package com.social.security.service;


import java.util.Map;

import com.social.security.model.dto.CredentialsDTO;
import com.social.security.model.dto.UserDTO;
import com.social.security.model.dto.UserInfoDTO;

import reactor.core.publisher.Mono;

public interface SecurityService {
    Mono<UserInfoDTO> signUp(UserDTO user);
    Mono<Map<String, Object>> signIn(CredentialsDTO credentials); 
}
