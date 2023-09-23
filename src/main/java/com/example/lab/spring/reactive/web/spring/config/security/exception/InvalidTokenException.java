package com.example.lab.spring.reactive.web.spring.config.security.exception;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.security.core.AuthenticationException;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@Value
public class InvalidTokenException extends AuthenticationException {

	@Serial
	private static final long serialVersionUID = -4790127447625190010L;

	ErrorCode errorCode;
	String message;

	public InvalidTokenException(ErrorCode errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
		this.message = message;
	}
}
