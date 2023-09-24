package com.example.lab.spring.reactive.web.spring.data.po;

import com.example.lab.spring.reactive.web.app.domain.ChatroomUserRole;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Data
@Table(name = "CHATROOM_USER")
public class ChatroomUserPo {

	@Id
	private Long id;

	@NotNull
	@Column("USER_ID")
	private Integer userId;

	@NotNull
	@Column("CHATROOM_ID")
	private Long chatroomId;

	private ChatroomUserRole role;

	@CreatedDate
	@Column("CREATE_TIME")
	private Instant createTime;
}
