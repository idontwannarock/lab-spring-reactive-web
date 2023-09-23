package com.example.lab.spring.reactive.web.spring.repository;

import com.example.lab.spring.reactive.web.app.domain.ChatroomStatus;
import com.example.lab.spring.reactive.web.app.gateway.ChatroomDataGateway;
import com.example.lab.spring.reactive.web.spring.data.dao.ChatroomDao;
import com.example.lab.spring.reactive.web.spring.data.po.ChatroomPo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class ChatroomRepository implements ChatroomDataGateway {

	private final ChatroomDao chatroomDao;

	@Transactional
	@Override
	public Mono<Long> createChatroom() {
		var chatroom = new ChatroomPo();
		chatroom.setStatus(ChatroomStatus.ACTIVE);
		return chatroomDao.save(chatroom).map(ChatroomPo::getId);
	}

	public Flux<ChatroomPo> findAll() {
		return chatroomDao.findAll();
	}
}
