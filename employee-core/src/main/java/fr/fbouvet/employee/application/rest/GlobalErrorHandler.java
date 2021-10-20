package fr.fbouvet.employee.application.rest;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_PLAIN;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.commandhandling.CommandExecutionException;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
@Order(-2)
@AllArgsConstructor
public class GlobalErrorHandler implements ErrorWebExceptionHandler {

  private ObjectMapper objectMapper;

  @Override
  public Mono<Void> handle(ServerWebExchange serverWebExchange, Throwable throwable) {

    DataBufferFactory bufferFactory = serverWebExchange.getResponse().bufferFactory();

    if (throwable instanceof CommandExecutionException) {
      serverWebExchange.getResponse().setStatusCode(BAD_REQUEST);
      DataBuffer dataBuffer = null;
      try {
        dataBuffer = bufferFactory.wrap(
            objectMapper.writeValueAsBytes(new HttpError(throwable.getMessage())));
      } catch (JsonProcessingException e) {
        dataBuffer = bufferFactory.wrap("".getBytes());
      }
      serverWebExchange.getResponse().getHeaders().setContentType(APPLICATION_JSON);
      return serverWebExchange.getResponse().writeWith(Mono.just(dataBuffer));
    }

    serverWebExchange.getResponse().setStatusCode(INTERNAL_SERVER_ERROR);
    serverWebExchange.getResponse().getHeaders().setContentType(TEXT_PLAIN);
    DataBuffer dataBuffer = bufferFactory.wrap("Unknown error".getBytes());
    return serverWebExchange.getResponse().writeWith(Mono.just(dataBuffer));
  }

  @Getter
  @AllArgsConstructor
  public static class HttpError {

    private String message;
  }
}
