package com.example.lab.spring.reactive.web.spring.data.po.converter;

import com.example.lab.spring.reactive.web.app.domain.MessageStatus;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.lang.NonNull;

public final class MessageStatusConverter {

    private MessageStatusConverter() {}

    @WritingConverter
    public static final class WriteConverter implements Converter<MessageStatus, Integer> {

        @Override
        public Integer convert(@NonNull MessageStatus source) {
            return source.value();
        }
    }

    @ReadingConverter
    public static final class ReadConverter implements Converter<Integer, MessageStatus> {

        @Override
        public MessageStatus convert(@NonNull Integer source) {
            return MessageStatus.of(source);
        }
    }
}
