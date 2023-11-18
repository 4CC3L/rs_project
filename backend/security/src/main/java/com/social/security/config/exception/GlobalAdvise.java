package com.social.security.config.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;

import com.social.security.utility.ErrorReponseUtil;
import com.social.security.utility.Utils;

import reactor.core.publisher.Mono;

@Component
@Order(-2)
public class GlobalAdvise implements WebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if(ex instanceof WebExchangeBindException) {
            return this.handleValidationException(exchange, (WebExchangeBindException) ex);
        }
        if(ex instanceof RuntimeException) {
            return this.handleRuntimeException(exchange, (RuntimeException) ex);
        }
        return Mono.error(ex);
    }

    private Mono<Void> handleRuntimeException(ServerWebExchange exchange, RuntimeException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> res = new HashMap<String, String>();
        res.put("message", ex.getMessage());
        return response.writeWith(Mono.just(response.bufferFactory().wrap(Utils.convertToByteArray(res))));
    }

    private Mono<Void> handleValidationException(ServerWebExchange exchange, WebExchangeBindException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        // Obtener los errores de validación
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(this::buildFieldErrorMessage)
                .reduce("", (s1, s2) -> s1 + "; " + s2);

        // Enviar una respuesta personalizada
        return ErrorReponseUtil.sendErrorResponse(exchange, status, errorMessage);
    }

    private String buildFieldErrorMessage(FieldError fieldError) {
        return fieldError.getField() + " " + fieldError.getDefaultMessage();
    }
    
}
