package com.example.lab.spring.reactive.web.spring.contoller.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateMessageRequest {

    @NotEmpty
    private String content;
}
