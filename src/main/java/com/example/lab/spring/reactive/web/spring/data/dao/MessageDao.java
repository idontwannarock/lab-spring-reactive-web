package com.example.lab.spring.reactive.web.spring.data.dao;

import com.example.lab.spring.reactive.web.spring.data.po.MessagePo;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface MessageDao extends ReactiveCrudRepository<MessagePo, Long> {

	Flux<MessagePo> findAllByChatroomId(Long chatroomId);
}
