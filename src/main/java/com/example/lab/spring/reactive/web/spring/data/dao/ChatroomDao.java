package com.example.lab.spring.reactive.web.spring.data.dao;

import com.example.lab.spring.reactive.web.spring.data.po.ChatroomPo;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ChatroomDao extends ReactiveCrudRepository<ChatroomPo, Long> {

	Flux<ChatroomPo> findAllByCreatorId(Integer userId);

	@Query("""
	SELECT c.*
	 FROM CHATROOM c
	 INNER JOIN CHATROOM_USER cu
	 ON c.ID = cu.CHATROOM_ID
	 WHERE c.ID = :chatroomId AND cu.USER_ID = :userId
	""")
	Mono<ChatroomPo> findByChatroomIdAndChatroomUserId(Long chatroomId, Integer userId);
}
