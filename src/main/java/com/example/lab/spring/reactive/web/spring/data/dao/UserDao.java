package com.example.lab.spring.reactive.web.spring.data.dao;

import com.example.lab.spring.reactive.web.spring.data.po.UserPo;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserDao extends ReactiveCrudRepository<UserPo, Integer> {
}
