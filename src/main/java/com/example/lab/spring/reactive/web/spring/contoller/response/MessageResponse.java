package com.example.lab.spring.reactive.web.spring.contoller.response;

import com.example.lab.spring.reactive.web.app.domain.ChatroomStatus;
import com.example.lab.spring.reactive.web.app.domain.MessageStatus;

import java.time.Instant;

public record MessageResponse(
	Long id,
	Long chatroomId,
	ChatroomStatus chatroomStatus,
	String content,
	MessageStatus status,
	Integer creatorId,
	Instant createTime
) {}
