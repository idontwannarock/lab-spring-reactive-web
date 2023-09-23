package com.example.lab.spring.reactive.web.spring.data.repository;

import com.example.lab.spring.reactive.web.spring.data.po.MessagePo;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface MessageRepository extends ReactiveCrudRepository<MessagePo, Long> {
}
