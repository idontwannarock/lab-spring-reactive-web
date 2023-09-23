package com.example.lab.spring.reactive.web.app.usecase;

import reactor.core.publisher.Mono;

public interface CreateMessageUseCase {

	Mono<Long> create(Long chatroomId, String content);
}
