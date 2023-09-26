package com.example.lab.spring.reactive.web.spring.contoller.response;

import lombok.Value;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SuppressWarnings("java:S1948")
@Value
public class ResponsePayload<T> implements Serializable {

	@Serial
	private static final long serialVersionUID = 1153203253665775084L;

	int status;
	T data;
	List<Status> messageList;

	public static <T> ResponsePayload<T> success(T data) {
		return new ResponsePayload<>(1, data, new ArrayList<>());
	}

	public static ResponsePayload<Void> error(Status... messageList) {
		return new ResponsePayload<>(-1, null, Arrays.asList(messageList));
	}

	private ResponsePayload(int code, T data, List<Status> messageList) {
		this.status = code;
		this.data = data;
		this.messageList = messageList;
	}

	public record Status(
		String code,
		String message,
		Map<String, Object> contentMap
	) implements Serializable {

		@Serial
		private static final long serialVersionUID = 7210280386438901569L;

		public static Status build(String message) {
			return new Status(null, message, null);
		}

		public static Status build(String code, String message, Map<String, Object> contentMap) {
			return new Status(code, message, contentMap);
		}
	}
}
