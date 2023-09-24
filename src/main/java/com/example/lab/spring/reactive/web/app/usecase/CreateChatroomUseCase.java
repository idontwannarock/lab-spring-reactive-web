package com.example.lab.spring.reactive.web.app.usecase;

import jakarta.validation.constraints.NotNull;
import reactor.core.publisher.Mono;

public interface CreateChatroomUseCase {

	Mono<Long> handle(@NotNull Integer userId);
}
