package com.example.lab.spring.reactive.web.spring.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
class JacksonConfig {

    @Primary
    @Bean
    ObjectMapper objectMapper() {
        return JsonMapper.builder()
                .build()
                .findAndRegisterModules()
                // write Java 8 Date/Time as standard timestamp in millisecond
                .configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
                // read standard timestamp in millisecond to Java 8 Date/Time
                .configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
    }
}
