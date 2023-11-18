package com.social.security.controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.social.security.model.dto.UserDTO;
import com.social.security.model.dto.UserInfoDTO;
import com.social.security.service.SecurityService;

import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/security")
public class SecurityController {

    private final SecurityService securityService;

    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @PostMapping("/sign-up")
    public Mono<UserInfoDTO> signUp(@RequestBody @Valid UserDTO dto) {
        return securityService.signUp(dto);
    }
    
}
