package com.social.security.service;

import com.social.security.model.dto.UserDTO;
import com.social.security.model.dto.UserInfoDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    
    Flux<UserInfoDTO> getAllUsers();
    Mono<UserInfoDTO> getUserById(Long userId);
    Mono<UserInfoDTO> getUserByUsername(String username);
    Mono<UserInfoDTO> createUser(UserDTO user);
    Mono<Void> updateUser(UserDTO user, Long userId);
    Mono<Void> deleteUser(Long userId);
}
