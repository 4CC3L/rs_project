package com.social.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.social.security.model.dto.UserInfoDTO;
import com.social.security.service.UserService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Flux<UserInfoDTO> findAll() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public Mono<UserInfoDTO> findUserById(@PathVariable("userId") Long userId) {
        return userService.getUserById(userId);
    }

    

}
