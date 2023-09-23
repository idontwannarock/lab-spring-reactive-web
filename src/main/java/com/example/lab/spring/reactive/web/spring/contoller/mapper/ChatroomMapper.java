package com.example.lab.spring.reactive.web.spring.contoller.mapper;

import com.example.lab.spring.reactive.web.spring.contoller.response.ChatroomResponse;
import com.example.lab.spring.reactive.web.spring.data.po.ChatroomPo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ChatroomMapper {

	ChatroomResponse toResponse(ChatroomPo chatroom);
}
