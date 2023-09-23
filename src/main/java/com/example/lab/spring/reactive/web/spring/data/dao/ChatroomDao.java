package com.example.lab.spring.reactive.web.spring.data.dao;

import com.example.lab.spring.reactive.web.spring.data.po.ChatroomPo;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ChatroomDao extends ReactiveCrudRepository<ChatroomPo, Long> {
}
