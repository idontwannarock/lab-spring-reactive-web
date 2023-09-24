package com.example.lab.spring.reactive.web.app.gateway;

import reactor.core.publisher.Mono;

public interface ChatroomDataGateway {

	Mono<Long> createChatroom(Integer creatorId);
}
