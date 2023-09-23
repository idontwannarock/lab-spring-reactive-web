package com.example.lab.spring.reactive.web.app.gateway;

import reactor.core.publisher.Mono;

public interface MessageDataGateway {

	Mono<Long> createMessage(Long chatroomId, String content);
}
