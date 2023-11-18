package com.social.security.config.exception;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;

import com.social.security.utility.ErrorReponseUtil;

import reactor.core.publisher.Mono;

@Component
@Order(-2)
public class GlobalAdvise implements WebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if(ex instanceof WebExchangeBindException) {
            return this.handleValidationException(exchange, (WebExchangeBindException) ex);
        }
        return Mono.error(ex);
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
