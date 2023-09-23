package com.example.lab.spring.reactive.web.spring.config.security.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Builder
@Data
public class AuthenticatedUser implements Serializable {

	@Serial
	private static final long serialVersionUID = 702316287983649760L;

	private Integer userId;
	private String userName;
}
