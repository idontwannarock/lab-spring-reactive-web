package com.example.lab.spring.reactive.web.spring.exception;

import com.example.lab.spring.reactive.web.spring.contoller.response.ResponsePayload;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandlers extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<ResponsePayload<Void>> handleResponseStatusException(ResponseStatusException e) {
		return ResponseEntity.status(e.getStatusCode()).body(ResponsePayload.error(ResponsePayload.Status.build(e.getMessage())));
	}

	// handle Spring Webflux generated exceptions
	@NonNull
	@Override
	protected Mono<ResponseEntity<Object>> handleExceptionInternal(
		@NonNull Exception ex, Object body, HttpHeaders headers,
		@NonNull HttpStatusCode statusCode, @NonNull ServerWebExchange exchange) {

		if (exchange.getResponse().isCommitted()) {
			return Mono.error(ex);
		}

		return createResponseEntity(generateNewBody(ex), headers, statusCode, exchange);
	}

	/**
	 * Generate body based on whether ex is instance of ErrorResponse.
	 *
	 * <p>Only following exceptions are not instance of {@link ErrorResponse}</p>
	 * <ul>
	 *     <li>ConversionNotSupportedException.class</li>
	 *     <li>TypeMismatchException.class</li>
	 *     <li>HttpMessageNotReadableException.class</li>
	 *     <li>HttpMessageNotWritableException.class</li>
	 *     <li>BindException.class</li>
	 * </ul>
	 */
	private Object generateNewBody(Exception ex) {
		String code = null;
		String message;
		Map<String, Object> contentMap = null;
		if (ex instanceof ErrorResponse errorResponse) {
			ProblemDetail body = errorResponse.getBody();
			// TODO: not sure about what to present in code, might need to change in the future
			code = body.getTitle();
			message = body.getDetail();
			contentMap = body.getProperties();
		} else {
			message = ex.getLocalizedMessage();
		}
		return ResponsePayload.error(ResponsePayload.Status.build(code, message, contentMap));
	}
}
