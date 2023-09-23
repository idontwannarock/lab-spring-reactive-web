package com.example.lab.spring.reactive.web.spring.contoller.response;

import com.example.lab.spring.reactive.web.app.domain.ChatroomStatus;

import java.time.Instant;

public record ChatroomResponse(
	Long id,
	ChatroomStatus status,
	Instant createTime
) {}
