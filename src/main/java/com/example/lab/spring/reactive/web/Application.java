package com.example.lab.spring.reactive.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration;
import reactor.core.publisher.Hooks;

@SpringBootApplication(exclude = {ReactiveUserDetailsServiceAutoConfiguration.class})
public class Application {

    public static void main(String[] args) {
		Hooks.enableAutomaticContextPropagation();
        SpringApplication.run(Application.class, args);
    }

}
