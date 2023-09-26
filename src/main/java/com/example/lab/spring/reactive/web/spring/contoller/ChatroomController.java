package com.example.lab.spring.reactive.web.spring.contoller;

import com.example.lab.spring.reactive.web.app.usecase.CreateChatroomUseCase;
import com.example.lab.spring.reactive.web.app.usecase.CreateMessageUseCase;
import com.example.lab.spring.reactive.web.spring.config.security.dto.AuthenticatedUser;
import com.example.lab.spring.reactive.web.spring.contoller.aop.annotation.ChatroomExists;
import com.example.lab.spring.reactive.web.spring.contoller.aop.annotation.UserExists;
import com.example.lab.spring.reactive.web.spring.contoller.mapper.ChatroomMapper;
import com.example.lab.spring.reactive.web.spring.contoller.mapper.MessageMapper;
import com.example.lab.spring.reactive.web.spring.contoller.request.CreateMessageRequest;
import com.example.lab.spring.reactive.web.spring.contoller.response.ChatroomResponse;
import com.example.lab.spring.reactive.web.spring.contoller.response.MessageResponse;
import com.example.lab.spring.reactive.web.spring.contoller.response.ResponsePayload;
import com.example.lab.spring.reactive.web.spring.repository.ChatroomRepository;
import com.example.lab.spring.reactive.web.spring.repository.MessageRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@Validated
@RequestMapping(path = "api/v1/chatrooms")
@RestController
public class ChatroomController {

	private final ChatroomRepository chatroomRepository;
	private final MessageRepository messageRepository;

	private final ChatroomMapper chatroomMapper;
	private final MessageMapper messageMapper;

	private final CreateChatroomUseCase createChatroomUseCase;

	@UserExists
	@PostMapping
	Mono<ResponsePayload<Long>> createChatroom(
		@AuthenticationPrincipal AuthenticatedUser currentUser) {
		return createChatroomUseCase.handle(currentUser.getUserId())
			.map(ResponsePayload::success);
	}

	@UserExists
	@GetMapping
	Mono<ResponsePayload<List<ChatroomResponse>>> findAllChatrooms(
		@AuthenticationPrincipal AuthenticatedUser currentUser) {
		return chatroomRepository.findAll(currentUser.getUserId())
			.map(chatroomMapper::toResponse)
			.collectList()
			.map(ResponsePayload::success);
	}

	private final CreateMessageUseCase createMessageUseCase;

	@ChatroomExists
	@PostMapping(path = "{chatroomId}/messages")
	Mono<ResponsePayload<Long>> createMessage(
		@AuthenticationPrincipal AuthenticatedUser currentUser,
		@NotNull @PathVariable Long chatroomId,
		@Valid @RequestBody CreateMessageRequest request) {
		return createMessageUseCase.create(currentUser.getUserId(), chatroomId, request.getContent()).map(ResponsePayload::success);
	}

	@ChatroomExists
	@GetMapping(path = "{chatroomId}/messages")
	Mono<ResponsePayload<List<MessageResponse>>> findMessagesById(
		@AuthenticationPrincipal AuthenticatedUser ignoreUser,
		@NotNull @PathVariable Long chatroomId) {
		return messageRepository.findAllByChatroomId(chatroomId)
			.map(messageMapper::toResponse)
			.collectList()
			.map(ResponsePayload::success);
	}
}
