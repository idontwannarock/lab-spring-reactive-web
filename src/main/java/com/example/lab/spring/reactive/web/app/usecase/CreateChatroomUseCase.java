package com.example.lab.spring.reactive.web.app.usecase;

import reactor.core.publisher.Mono;

public interface CreateChatroomUseCase {

	Mono<Long> handle();
}
