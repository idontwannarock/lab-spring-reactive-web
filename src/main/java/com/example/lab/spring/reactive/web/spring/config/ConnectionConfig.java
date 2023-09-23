package com.example.lab.spring.reactive.web.spring.config;

import io.asyncer.r2dbc.mysql.MySqlConnectionConfiguration;
import io.asyncer.r2dbc.mysql.MySqlConnectionFactory;
import io.asyncer.r2dbc.mysql.constant.SslMode;
import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.proxy.ProxyConnectionFactory;
import io.r2dbc.proxy.support.QueryExecutionInfoFormatter;
import io.r2dbc.spi.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

import java.time.Duration;
import java.time.ZoneId;

@Slf4j
@Configuration
class ConnectionConfig {

    @Bean
    ConnectionFactory connectionFactory(
            @Value("${spring.r2dbc.host}") String host,
            @Value("${spring.r2dbc.port}") int port,
            @Value("${spring.r2dbc.database}") String database,
            @Value("${spring.r2dbc.username}") String username,
            @Value("${spring.r2dbc.password}") String password,
            @Value("${spring.r2dbc.connection-timeout-in-seconds}") long connectionTimeout) {
        ConnectionFactory original = MySqlConnectionFactory.from(MySqlConnectionConfiguration.builder()
                .host(host)
                .port(port)
                .database(database)
                .serverZoneId(ZoneId.of("UTC"))
                .sslMode(SslMode.DISABLED)
                .username(username)
                .password(password)
                .connectTimeout(Duration.ofSeconds(connectionTimeout))
                .tcpKeepAlive(true)
                .build());

        QueryExecutionInfoFormatter formatter = QueryExecutionInfoFormatter.showAll();
        return ProxyConnectionFactory.builder(original)
                .onAfterQuery(queryInfo -> log.info(formatter.format(queryInfo)))
                .build();
    }

    @Bean
    ConnectionPool connectionPool(
            ConnectionFactory connectionFactory,
            @Value("${spring.r2dbc.pool.initial-size}") int initSize,
            @Value("${spring.r2dbc.pool.min-idle}") int minIdle,
            @Value("${spring.r2dbc.pool.max-size}") int maxSize,
            @Value("${spring.r2dbc.pool.max-idle-time-in-seconds}") int maxIdleTime,
            @Value("${spring.r2dbc.pool.max-life-time-in-seconds}") int maxLifeTime) {
        return new ConnectionPool(ConnectionPoolConfiguration.builder()
                .connectionFactory(connectionFactory)
                .initialSize(initSize)
                .minIdle(minIdle)
                .maxSize(maxSize)
                .maxIdleTime(Duration.ofSeconds(maxIdleTime))
                .maxLifeTime(Duration.ofSeconds(maxLifeTime))
                .validationQuery("SET NAMES utf8mb4")
                .build());
    }

    @Bean
    ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
        var populator = new ResourceDatabasePopulator(new ClassPathResource("schema.sql"));
        var initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        initializer.setDatabasePopulator(populator);
        return initializer;
    }
}
