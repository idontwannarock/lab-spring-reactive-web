package com.example.lab.spring.reactive.web.spring.data.dao;

import com.example.lab.spring.reactive.web.spring.data.po.UserPo;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserDao extends ReactiveCrudRepository<UserPo, Long> {

	Mono<Boolean> existsByUserId(Integer userId);
}
