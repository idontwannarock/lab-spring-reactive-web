package com.example.lab.spring.reactive.web.spring.data.po.converter;

import com.example.lab.spring.reactive.web.app.domain.ChatroomStatus;
import com.example.lab.spring.reactive.web.app.domain.MessageStatus;
import com.example.lab.spring.reactive.web.spring.data.po.MessageFullInfoView;
import io.r2dbc.spi.Row;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.time.Instant;

@ReadingConverter
public class MessageFullInfoViewConverter implements Converter<Row, MessageFullInfoView> {

	@Override
	public MessageFullInfoView convert(Row source) {
		return new MessageFullInfoView(
			source.get("ID", Long.class),
			source.get("CHATROOM_ID", Long.class),
			ChatroomStatus.of(source.get("chatroomStatus", Integer.class)),
			source.get("CONTENT", String.class),
			MessageStatus.of(source.get("STATUS", Integer.class)),
			source.get("CREATOR_ID", Integer.class),
			source.get("CREATE_TIME", Instant.class));
	}
}
