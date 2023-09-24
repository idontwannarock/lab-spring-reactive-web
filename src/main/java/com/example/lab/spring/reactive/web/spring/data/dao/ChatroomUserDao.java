package com.example.lab.spring.reactive.web.spring.data.dao;

import com.example.lab.spring.reactive.web.spring.data.po.ChatroomUserPo;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ChatroomUserDao extends ReactiveCrudRepository<ChatroomUserPo, Integer> {

	Mono<ChatroomUserPo> findByChatroomIdAndUserId(Long chatroomId, Integer userId);
}
