package com.example.lab.spring.reactive.web.spring.contoller;

import com.example.lab.spring.reactive.web.app.usecase.CreateChatroomUseCase;
import com.example.lab.spring.reactive.web.app.usecase.CreateMessageUseCase;
import com.example.lab.spring.reactive.web.spring.config.security.dto.AuthenticatedUser;
import com.example.lab.spring.reactive.web.spring.contoller.mapper.ChatroomMapper;
import com.example.lab.spring.reactive.web.spring.contoller.mapper.MessageMapper;
import com.example.lab.spring.reactive.web.spring.contoller.request.CreateMessageRequest;
import com.example.lab.spring.reactive.web.spring.contoller.response.ChatroomResponse;
import com.example.lab.spring.reactive.web.spring.contoller.response.MessageResponse;
import com.example.lab.spring.reactive.web.spring.repository.ChatroomRepository;
import com.example.lab.spring.reactive.web.spring.repository.MessageRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

	@PostMapping
	Mono<Long> createChatroom(
		@AuthenticationPrincipal AuthenticatedUser currentUser) {
		return createChatroomUseCase.handle(currentUser.getUserId());
	}

	@GetMapping
	Flux<ChatroomResponse> findAllChatrooms(
		@AuthenticationPrincipal AuthenticatedUser currentUser) {
		return chatroomRepository.findAll(currentUser.getUserId()).map(chatroomMapper::toResponse);
	}

	private final CreateMessageUseCase createMessageUseCase;

	@PostMapping(path = "{chatroomId}/messages")
	Mono<Long> createMessage(
		@AuthenticationPrincipal AuthenticatedUser currentUser,
		@NotNull @PathVariable Long chatroomId,
		@Valid @RequestBody CreateMessageRequest request) {
		return createMessageUseCase.create(currentUser.getUserId(), chatroomId, request.getContent());
	}

	@GetMapping(path = "{chatroomId}/messages")
	Flux<MessageResponse> findMessagesById(
		@AuthenticationPrincipal AuthenticatedUser currentUser,
		@NotNull @PathVariable Long chatroomId) {
		return messageRepository.findAllByChatroomId(currentUser.getUserId(), chatroomId).map(messageMapper::toResponse);
	}
}
