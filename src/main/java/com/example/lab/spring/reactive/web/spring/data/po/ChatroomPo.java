package com.example.lab.spring.reactive.web.spring.data.po;

import com.example.lab.spring.reactive.web.app.domain.ChatroomStatus;
import jakarta.validation.constraints.NotNull;
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

	@NotNull
	private ChatroomStatus status;

	@NotNull
	@Column("CREATOR_ID")
	private Integer creatorId;

	@CreatedDate
	@Column("CREATE_TIME")
	private Instant createTime;
}
