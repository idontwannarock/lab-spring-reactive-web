package com.example.lab.spring.reactive.web.spring.contoller.response;

import com.example.lab.spring.reactive.web.app.domain.MessageStatus;

import java.time.Instant;

public record MessageResponse(
	Long id,
	String content,
	MessageStatus status,
	Instant createTime
) {}
