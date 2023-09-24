package com.example.lab.spring.reactive.web.spring.repository;

import com.example.lab.spring.reactive.web.app.domain.ChatroomStatus;
import com.example.lab.spring.reactive.web.app.domain.ChatroomUserRole;
import com.example.lab.spring.reactive.web.app.gateway.ChatroomDataGateway;
import com.example.lab.spring.reactive.web.spring.data.dao.ChatroomDao;
import com.example.lab.spring.reactive.web.spring.data.dao.ChatroomUserDao;
import com.example.lab.spring.reactive.web.spring.data.po.ChatroomPo;
import com.example.lab.spring.reactive.web.spring.data.po.ChatroomUserPo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
@Component
public class ChatroomRepository implements ChatroomDataGateway {

	private final ChatroomDao chatroomDao;
	private final ChatroomUserDao chatroomUserDao;

	private final Function<Integer, Mono<ChatroomPo>> createChatroom = userId -> {
		var chatroom = new ChatroomPo();
		chatroom.setStatus(ChatroomStatus.ACTIVE);
		chatroom.setCreatorId(userId);
		return Mono.just(chatroom);
	};

	private final Function<ChatroomPo, Mono<ChatroomUserPo>> assignChatroomOwner = chatroom -> {
		var chatroomUser = new ChatroomUserPo();
		chatroomUser.setChatroomId(chatroom.getId());
		chatroomUser.setUserId(chatroom.getCreatorId());
		chatroomUser.setRole(ChatroomUserRole.OWNER);
		return Mono.just(chatroomUser);
	};

	@Transactional
	@Override
	public Mono<Long> createChatroom(Integer creatorId) {
		return Mono.justOrEmpty(creatorId)
			.switchIfEmpty(Mono.error(new ServerWebInputException("chatroom creator id is required")))
			.flatMap(createChatroom)
			.flatMap(chatroomDao::save)
			.switchIfEmpty(Mono.error(new ServerWebInputException("chatroom creation failed")))
			.flatMap(assignChatroomOwner)
			.flatMap(chatroomUserDao::save)
			.switchIfEmpty(Mono.error(new ServerWebInputException("chatroom owner assignment failed")))
			.map(ChatroomUserPo::getChatroomId);
	}

	public Flux<ChatroomPo> findAll(Integer userId) {
		return chatroomDao.findAllByCreatorId(userId);
	}
}
