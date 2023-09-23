package com.example.lab.spring.reactive.web.spring.data.dao;

import com.example.lab.spring.reactive.web.spring.data.po.MessagePo;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface MessageDao extends ReactiveCrudRepository<MessagePo, Long> {
}
