package com.example.lab.spring.reactive.web.app.usecase.impl;

import com.example.lab.spring.reactive.web.app.gateway.ChatroomDataGateway;
import com.example.lab.spring.reactive.web.app.usecase.CreateChatroomUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class CreateChatroomUseCaseImpl implements CreateChatroomUseCase {

	private final ChatroomDataGateway chatroomDataGateway;

	@Override
	public Mono<Long> handle() {
		return chatroomDataGateway.createChatroom();
	}
}
