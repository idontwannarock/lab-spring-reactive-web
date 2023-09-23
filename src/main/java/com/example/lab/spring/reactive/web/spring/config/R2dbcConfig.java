package com.example.lab.spring.reactive.web.spring.config;

import com.example.lab.spring.reactive.web.spring.data.po.converter.InstantConverter;
import com.example.lab.spring.reactive.web.spring.data.po.converter.MessageStatusConverter;
import io.r2dbc.spi.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.lang.NonNull;

import java.util.List;

@RequiredArgsConstructor
@Configuration
class R2dbcConfig extends AbstractR2dbcConfiguration {

    private final ConnectionFactory connectionFactory;

    @NonNull
    @Override
    public ConnectionFactory connectionFactory() {
        return this.connectionFactory;
    }

    @NonNull
    @Override
    protected List<Object> getCustomConverters() {
        return List.of(
                new MessageStatusConverter.WriteConverter(),
                new MessageStatusConverter.ReadConverter(),
                new InstantConverter.WriteConverter(),
                new InstantConverter.ReadConverter());
    }
}
