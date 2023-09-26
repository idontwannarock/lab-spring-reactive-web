package com.example.lab.spring.reactive.web.spring.data.dao;

import com.example.lab.spring.reactive.web.spring.data.po.MessageFullInfoView;
import com.example.lab.spring.reactive.web.spring.data.po.MessagePo;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface MessageDao extends ReactiveCrudRepository<MessagePo, Long> {

	@Query("""
	SELECT m.*, c.STATUS AS chatroomStatus
	 FROM MESSAGE m
	 INNER JOIN CHATROOM c ON m.CHATROOM_ID = c.ID
	 WHERE c.ID = :chatroomId""")
	Flux<MessageFullInfoView> findAllFullViewByChatroomId(Long chatroomId);
}
