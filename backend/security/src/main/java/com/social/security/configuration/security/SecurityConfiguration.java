package com.social.security.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.social.security.configuration.security.userDetails.UserDetailsService;


/* @Configuration
@EnableWebSecurity
@EnableReactiveMethodSecurity(useAuthorizationManager=true) */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

/*     @Autowired
    AuthenticationSuccessHandler authenticationSuccessHandler; */

    @Autowired
    UserDetailsService userDetailsService;

    @Bean
    PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

    @Bean
    SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) {
        http
        .csrf(csrf -> csrf.disable())
        .authorizeExchange((authorize) -> {
            authorize
            .anyExchange().permitAll()
            .anyExchange().hasRole("ADMIN");
        })
        .addFilterBefore(null, null);
        return http.build();
    }

    @Bean
    ReactiveAuthenticationManager authenticationManager(MapReactiveUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        UserDetailsRepositoryReactiveAuthenticationManager authentication = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        authentication.setPasswordEncoder(passwordEncoder());
        return authentication;
    }



}