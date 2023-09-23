package com.example.lab.spring.reactive.web.spring.data.po.converter;

import com.example.lab.spring.reactive.web.app.domain.ChatroomStatus;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.lang.NonNull;

public class ChatroomStatusConverter {

	private ChatroomStatusConverter() {}

	@WritingConverter
	public static final class WriteConverter implements Converter<ChatroomStatus, Integer> {

		@Override
		public Integer convert(@NonNull ChatroomStatus source) {
			return source.value();
		}
	}

	@ReadingConverter
	public static final class ReadConverter implements Converter<Integer, ChatroomStatus> {

		@Override
		public ChatroomStatus convert(@NonNull Integer source) {
			return ChatroomStatus.of(source);
		}
	}
}
