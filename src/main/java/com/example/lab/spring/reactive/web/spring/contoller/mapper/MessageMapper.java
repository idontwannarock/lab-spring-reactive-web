package com.example.lab.spring.reactive.web.spring.contoller.mapper;

import com.example.lab.spring.reactive.web.spring.contoller.response.MessageResponse;
import com.example.lab.spring.reactive.web.spring.data.po.MessagePo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MessageMapper {

	MessageResponse toResponse(MessagePo message);
}
