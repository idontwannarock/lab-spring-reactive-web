package com.example.lab.spring.reactive.web.spring.data.po.converter;

import com.example.lab.spring.reactive.web.app.domain.ChatroomUserRole;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.lang.NonNull;

public final class ChatroomUserRoleConverter {

	@WritingConverter
	public static final class WriteConverter implements Converter<ChatroomUserRole, Integer> {

		@Override
		public Integer convert(@NonNull ChatroomUserRole source) {
			return source.value();
		}
	}

	@ReadingConverter
	public static final class ReadConverter implements Converter<Integer, ChatroomUserRole> {

		@Override
		public ChatroomUserRole convert(@NonNull Integer source) {
			return ChatroomUserRole.of(source);
		}
	}
}
