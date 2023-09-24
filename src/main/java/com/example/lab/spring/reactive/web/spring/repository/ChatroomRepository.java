package com.example.lab.spring.reactive.web.spring.repository;

import com.example.lab.spring.reactive.web.app.domain.ChatroomStatus;
import com.example.lab.spring.reactive.web.app.domain.ChatroomUserRole;
import com.example.lab.spring.reactive.web.app.gateway.ChatroomDataGateway;
import com.example.lab.spring.reactive.web.spring.data.dao.ChatroomDao;
import com.example.lab.spring.reactive.web.spring.data.dao.ChatroomUserDao;
import com.example.lab.spring.reactive.web.spring.data.dao.UserDao;
import com.example.lab.spring.reactive.web.spring.data.po.ChatroomPo;
import com.example.lab.spring.reactive.web.spring.data.po.ChatroomUserPo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class ChatroomRepository implements ChatroomDataGateway {

	private final UserDao userDao;
	private final ChatroomDao chatroomDao;
	private final ChatroomUserDao chatroomUserDao;

	@Transactional
	@Override
	public Mono<Long> createChatroom(Integer creatorId) {
		return userDao.findById(creatorId)
			.switchIfEmpty(Mono.error(new ServerWebInputException("user not found")))
			.map(user -> {
				var chatroom = new ChatroomPo();
				chatroom.setStatus(ChatroomStatus.ACTIVE);
				chatroom.setCreatorId(creatorId);
				return chatroomDao.save(chatroom);
			})
			.flatMap(chatroom -> chatroom)
			.map(ChatroomPo::getId)
			.switchIfEmpty(Mono.error(new ServerWebInputException("chatroom creation failed with no generated id")))
			.map(chatroomId -> chatroomUserDao.findByChatroomIdAndUserId(chatroomId, creatorId)
				.switchIfEmpty(Mono.fromSupplier(() -> {
					var chatroomUser = new ChatroomUserPo();
					chatroomUser.setChatroomId(chatroomId);
					chatroomUser.setUserId(creatorId);
					chatroomUser.setRole(ChatroomUserRole.OWNER);
					return chatroomUserDao.save(chatroomUser);
				}).flatMap(chatroomUser -> chatroomUser)))
			.flatMap(chatroomUser -> chatroomUser)
			.switchIfEmpty(Mono.error(new ServerWebInputException("chatroom user creation failed")))
			.mapNotNull(ChatroomUserPo::getChatroomId);
	}

	public Flux<ChatroomPo> findAll(Integer userId) {
		return chatroomDao.findAllByCreatorId(userId);
	}
}
