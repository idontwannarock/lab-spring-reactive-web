package com.example.lab.spring.reactive.web.spring.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * Handling unauthorized request in security filter chain
 */
@Slf4j
@Component
public class CustomServerAccessDeniedHandler implements ServerAccessDeniedHandler {

	@Override
	public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
		log.debug("Unauthorized: {}", denied.getMessage());

		String message = "{\"data\": null, \"status\": {\"code\": \"-1\", \"message\": \"Access Denied\"}}";
		byte[] messageInBytes = message.getBytes(StandardCharsets.UTF_8);
		DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(messageInBytes);

		return Mono.just(message)
			.flatMap(transformer -> {
				ServerHttpResponse response = exchange.getResponse();
				response.setStatusCode(HttpStatus.FORBIDDEN);
				response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
				return response.writeWith(Mono.just(buffer));
			}).doOnSuccess(empty -> DataBufferUtils.release(buffer));
	}
}
