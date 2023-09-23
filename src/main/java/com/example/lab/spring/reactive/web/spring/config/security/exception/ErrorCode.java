package com.example.lab.spring.reactive.web.spring.config.security.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
	INVALID_JWT_TOKEN("ERR04002"),
	EXPIRED_JWT_TOKEN("ERR04003"),
	UNSUPPORTED_JWT_TOKEN("ERR04004"),
	INVALID_JWT_COMPACT_HANDLER("ERR04005"),
	INVALID_JWT_SIGNATURE("ERR04006"),
	UNEXPECTED_JWT_TOKEN_ERROR("ERR04007")
	;

	private final String code;

	ErrorCode(String code) {
		this.code = code;
	}
}
