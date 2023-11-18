package com.social.security.utility;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

public class ErrorReponseUtil {

    public static Mono<Void> sendErrorResponse(ServerWebExchange exchange, HttpStatus status, String errorMessage) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        // Construir el cuerpo de la respuesta de error
        String responseBody = "{\"status\": \"" + status.value() + "\", \"error\": \"" + status.getReasonPhrase() + "\", \"message\": \"" + errorMessage + "\"}";

        // Escribir la respuesta en el cuerpo
        return response.writeWith(Mono.just(response.bufferFactory().wrap(responseBody.getBytes())));
    }
    
}
