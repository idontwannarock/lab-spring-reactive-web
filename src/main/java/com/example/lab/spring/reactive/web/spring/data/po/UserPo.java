package com.example.lab.spring.reactive.web.spring.data.po;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Data
@Table(name = "USER")
public class UserPo {

	@NotNull
	@Id
	private Integer id;

	@CreatedDate
	@Column("CREATE_TIME")
	private Instant createTime;
}
