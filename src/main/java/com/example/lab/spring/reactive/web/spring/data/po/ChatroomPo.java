package com.example.lab.spring.reactive.web.spring.data.po;

import com.example.lab.spring.reactive.web.app.domain.ChatroomStatus;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Data
@Table(name = "CHATROOM")
public class ChatroomPo {

	@Id
	private Long id;

	private ChatroomStatus status;

	@CreatedDate
	@Column("CREATE_TIME")
	private Instant createTime;
}
