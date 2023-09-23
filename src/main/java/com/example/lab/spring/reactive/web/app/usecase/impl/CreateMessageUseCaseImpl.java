package com.example.lab.spring.reactive.web.app.usecase.impl;

import com.example.lab.spring.reactive.web.app.gateway.MessageDataGateway;
import com.example.lab.spring.reactive.web.app.usecase.CreateMessageUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class CreateMessageUseCaseImpl implements CreateMessageUseCase {

	private final MessageDataGateway messageDataGateway;

	@Override
	public Mono<Long> create(Long chatroomId, String content) {
		return messageDataGateway.createMessage(chatroomId, content);
	}
}
