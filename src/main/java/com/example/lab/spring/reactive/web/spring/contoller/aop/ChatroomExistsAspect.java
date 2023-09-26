package com.example.lab.spring.reactive.web.spring.contoller.aop;

import com.example.lab.spring.reactive.web.spring.repository.ChatroomRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Order(20)
@Component
@Aspect
public class ChatroomExistsAspect extends ValidationAspectBase {

	private final ChatroomRepository chatroomRepository;

	@Pointcut("""
	@annotation(com.example.lab.spring.reactive.web.spring.contoller.aop.annotation.ChatroomExists)
	 && execution(* com.example.lab.spring.reactive.web.spring.contoller.*.*(*,Long,..))""")
	public void pointcut() {}

	@Around(value = "pointcut()", argNames = "joinPoint")
	public Object checkIsChatroomExists(ProceedingJoinPoint joinPoint) {
		Mono<Boolean> validation = Mono.justOrEmpty(joinPoint.getArgs()[1])
			.switchIfEmpty(Mono.error(new ServerWebInputException("chatroom id is required")))
			.map(Long.class::cast)
			.map(chatroomRepository::existsById)
			.flatMap(mono -> mono)
			.filter(Boolean.TRUE::equals)
			.switchIfEmpty(Mono.error(new ServerWebInputException("chatroom not found")));
		return proceedWithValidation(joinPoint, validation);
	}
}
