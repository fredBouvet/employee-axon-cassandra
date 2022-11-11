package fr.fbouvet.employee.application.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.messaging.HandlerExecutionException;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_PLAIN;

@Configuration
@Order(-2)
@AllArgsConstructor
public class GlobalErrorHandler implements ErrorWebExceptionHandler {

    private ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, Throwable throwable) {

        ServerHttpResponse response = serverWebExchange.getResponse();
        DataBufferFactory bufferFactory = response.bufferFactory();

        if (throwable instanceof HandlerExecutionException) {
            response.setStatusCode(BAD_REQUEST);
            response.getHeaders().setContentType(APPLICATION_JSON);
            return response.writeWith(Mono.just(getDataBuffer(throwable, bufferFactory)));
        }

        response.setStatusCode(INTERNAL_SERVER_ERROR);
        response.getHeaders().setContentType(TEXT_PLAIN);
        DataBuffer dataBuffer = bufferFactory.wrap("Unknown error".getBytes());
        return response.writeWith(Mono.just(dataBuffer));
    }

    private DataBuffer getDataBuffer(Throwable throwable, DataBufferFactory bufferFactory) {
        DataBuffer dataBuffer;
        try {
            dataBuffer = bufferFactory.wrap(
                    objectMapper.writeValueAsBytes(new HttpError(throwable.getMessage())));
        } catch (JsonProcessingException e) {
            dataBuffer = bufferFactory.wrap("".getBytes());
        }
        return dataBuffer;
    }

    @Getter
    @AllArgsConstructor
    public static class HttpError {

        private String message;
    }
}
