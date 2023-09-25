package com.example.lab.spring.reactive.web.spring.repository;

import com.example.lab.spring.reactive.web.spring.data.dao.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class UserRepository {

	private final UserDao userDao;

	public Mono<Boolean> existsById(Integer userId) {
		return userDao.existsByUserId(userId);
	}
}
