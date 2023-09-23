package com.example.lab.spring.reactive.web.spring.config.security;

import com.example.lab.spring.reactive.web.spring.config.security.exception.InvalidTokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * Handling unauthenticated request in security filter chain
 */
@Slf4j
@Component
public class CustomServerAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

	@Override
	public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
		log.debug("Unauthenticated: {}", ex.getMessage());

		String code = "-1";
		if (ex instanceof InvalidTokenException e) {
			code = e.getErrorCode().getCode();
		}

		String message = "{\"data\": null, \"status\": {\"code\": \"" + code + "\", \"message\": \"" + ex.getMessage() + "\"}}";
		byte[] messageInBytes = message.getBytes(StandardCharsets.UTF_8);
		DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(messageInBytes);

		return Mono.just(code)
			.flatMap(transformer -> {
				ServerHttpResponse response = exchange.getResponse();
				response.setStatusCode(HttpStatus.UNAUTHORIZED);
				response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
				return response.writeWith(Mono.just(buffer));
			}).doOnSuccess(empty -> DataBufferUtils.release(buffer));
	}
}
