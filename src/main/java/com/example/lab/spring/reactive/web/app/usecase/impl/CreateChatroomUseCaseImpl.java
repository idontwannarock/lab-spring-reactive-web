package com.example.lab.spring.reactive.web.app.usecase.impl;

import com.example.lab.spring.reactive.web.app.gateway.ChatroomDataGateway;
import com.example.lab.spring.reactive.web.app.usecase.CreateChatroomUseCase;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Validated
@Service
public class CreateChatroomUseCaseImpl implements CreateChatroomUseCase {

	private final ChatroomDataGateway chatroomDataGateway;

	@Override
	public Mono<Long> handle(@NotNull Integer userId) {
		return chatroomDataGateway.createChatroom(userId);
	}
}
