package com.example.lab.spring.reactive.web.spring.data.po;

import com.example.lab.spring.reactive.web.app.domain.MessageStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Data
@Table(name = "MESSAGE")
public class MessagePo {

	@Id
	private Long id;

	@NotNull
	@Column("CHATROOM_ID")
	private Long chatroomId;

	private String content;

	@NotNull
	private MessageStatus status;

	@NotNull
	private Integer creatorId;

	@CreatedDate
	@Column("CREATE_TIME")
	private Instant createTime;
}
