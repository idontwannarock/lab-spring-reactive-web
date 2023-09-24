package com.example.lab.spring.reactive.web.spring.data.dao;

import com.example.lab.spring.reactive.web.spring.data.po.ChatroomPo;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ChatroomDao extends ReactiveCrudRepository<ChatroomPo, Long> {

	Flux<ChatroomPo> findAllByCreatorId(Integer userId);
}
