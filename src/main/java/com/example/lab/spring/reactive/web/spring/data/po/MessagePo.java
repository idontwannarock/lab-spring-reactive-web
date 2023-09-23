package com.example.lab.spring.reactive.web.spring.data.po;

import com.example.lab.spring.reactive.web.app.domain.MessageStatus;
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

	private String content;

	private MessageStatus status;

	@CreatedDate
	@Column("CREATE_TIME")
	private Instant createTime;
}
