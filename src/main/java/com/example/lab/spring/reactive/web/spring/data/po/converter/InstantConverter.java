package com.example.lab.spring.reactive.web.spring.data.po.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.lang.NonNull;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class InstantConverter {

    private InstantConverter() {}

    @WritingConverter
    public static final class WriteConverter implements Converter<Instant, LocalDateTime> {

        @Override
        public LocalDateTime convert(@NonNull Instant source) {
            return source.atOffset(ZoneOffset.UTC).toLocalDateTime();
        }
    }

    @ReadingConverter
    public static final class ReadConverter implements Converter<LocalDateTime, Instant> {

        @Override
        public Instant convert(@NonNull LocalDateTime source) {
            return source.toInstant(ZoneOffset.UTC);
        }
    }
}
