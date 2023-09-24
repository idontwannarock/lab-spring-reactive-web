package com.example.lab.spring.reactive.web.spring.repository;

import com.example.lab.spring.reactive.web.app.domain.MessageStatus;
import com.example.lab.spring.reactive.web.app.gateway.MessageDataGateway;
import com.example.lab.spring.reactive.web.spring.data.dao.ChatroomDao;
import com.example.lab.spring.reactive.web.spring.data.dao.MessageDao;
import com.example.lab.spring.reactive.web.spring.data.po.MessagePo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

@RequiredArgsConstructor
@Component
public class MessageRepository implements MessageDataGateway {

	private final ChatroomDao chatroomDao;
	private final MessageDao messageDao;

	@Transactional
	@Override
	public Mono<Long> createMessage(Integer creatorId, Long chatroomId, String content) {
		var message = new MessagePo();
		message.setChatroomId(chatroomId);
		message.setContent(content);
		message.setStatus(MessageStatus.ACTIVE);
		message.setCreatorId(creatorId);
		message.setCreateTime(Instant.now());
		return messageDao.save(message).map(MessagePo::getId);
	}

	public Flux<MessagePo> findAllByChatroomId(Integer userId, Long chatroomId) {
		return chatroomDao.findByChatroomIdAndChatroomUserId(chatroomId, userId)
			.switchIfEmpty(Mono.error(new ServerWebInputException("chatroom not found")))
			.map(chatroom -> messageDao.findAllByChatroomId(chatroom.getId()))
			.switchIfEmpty(Mono.error(new ServerWebInputException("message not found")))
			.flatMapMany(Flux::from);
	}
}
