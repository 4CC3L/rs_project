package com.social.security.configuration.security.authentication;
/* package com.social.security.configuration.security;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import io.netty.util.CharsetUtil;
import reactor.core.publisher.Mono;

// Utilizado comunmente en aplicaciones Spring MVC o que manejen vistas renderizadas desde el servidor

@Component
public class AuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        DataBuffer dataBuffer = response.bufferFactory().wrap(username.toString().getBytes(CharsetUtil.UTF_8));
        return response.writeWith(Mono.just(dataBuffer));
    }
    
} */
