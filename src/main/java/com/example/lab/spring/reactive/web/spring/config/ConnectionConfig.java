package com.example.lab.spring.reactive.web.spring.config;

import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.proxy.ProxyConnectionFactory;
import io.r2dbc.proxy.support.QueryExecutionInfoFormatter;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.r2dbc.ConnectionFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

import java.time.Duration;

@Slf4j
@Configuration
class ConnectionConfig {

	@ConditionalOnProperty(name = "logging.query-detail")
	@Primary
	@Bean
	ConnectionFactory connectionFactory(
		@Value("${spring.r2dbc.url}") String url,
		@Value("${spring.r2dbc.username}") String username,
		@Value("${spring.r2dbc.password}") String password,
		@Value("${spring.r2dbc.connection-timeout-in-seconds}") long connectTimeoutInSeconds,
		@Value("${spring.r2dbc.pool.initial-size}") int initSize,
		@Value("${spring.r2dbc.pool.min-idle}") int minIdle,
		@Value("${spring.r2dbc.pool.max-size}") int maxSize) {
		ConnectionFactory original = ConnectionFactoryBuilder
			.withOptions(ConnectionFactoryOptions.parse(url).mutate())
			.username(username)
			.password(password)
			.configure(builder -> builder.option(ConnectionFactoryOptions.CONNECT_TIMEOUT, Duration.ofSeconds(connectTimeoutInSeconds)))
			.build();

		QueryExecutionInfoFormatter formatter = QueryExecutionInfoFormatter.showAll();
		ConnectionFactory connectionFactory = ProxyConnectionFactory.builder(original)
			.onAfterQuery(queryInfo -> log.info(formatter.format(queryInfo)))
			.build();

		return new ConnectionPool(ConnectionPoolConfiguration.builder()
			.connectionFactory(connectionFactory)
			.initialSize(initSize)
			.minIdle(minIdle)
			.maxSize(maxSize)
			.validationQuery("SET NAMES utf8mb4")
			.build());
	}

	@ConditionalOnProperty(name = "logging.query-detail", havingValue = "false", matchIfMissing = true)
	@Primary
	@Bean
	ConnectionPool connectionPool(
		@Value("${spring.r2dbc.url}") String url,
		@Value("${spring.r2dbc.username}") String username,
		@Value("${spring.r2dbc.password}") String password,
		@Value("${spring.r2dbc.connection-timeout-in-seconds}") long connectTimeoutInSeconds,
		@Value("${spring.r2dbc.pool.initial-size}") int initSize,
		@Value("${spring.r2dbc.pool.min-idle}") int minIdle,
		@Value("${spring.r2dbc.pool.max-size}") int maxSize) {
		ConnectionFactory original = ConnectionFactoryBuilder
			.withOptions(ConnectionFactoryOptions.parse(url).mutate())
			.username(username)
			.password(password)
			.configure(builder -> builder.option(ConnectionFactoryOptions.CONNECT_TIMEOUT, Duration.ofSeconds(connectTimeoutInSeconds)))
			.build();

		return new ConnectionPool(ConnectionPoolConfiguration.builder()
			.connectionFactory(original)
			.initialSize(initSize)
			.minIdle(minIdle)
			.maxSize(maxSize)
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
