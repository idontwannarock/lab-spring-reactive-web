package com.example.lab.spring.reactive.web.spring.contoller.aop;

import com.example.lab.spring.reactive.web.spring.config.security.dto.AuthenticatedUser;
import com.example.lab.spring.reactive.web.spring.repository.UserRepository;
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
@Order(10)
@Component
@Aspect
public class UserExistsAspect extends ValidationAspectBase {

	private final UserRepository userRepository;

	@Pointcut("""
	(@annotation(com.example.lab.spring.reactive.web.spring.contoller.aop.annotation.ChatroomExists)
	 || @annotation(com.example.lab.spring.reactive.web.spring.contoller.aop.annotation.UserExists))
	 && args(currentUser,..)""")
	public void pointcut(AuthenticatedUser currentUser) {}

	@Around(value = "pointcut(currentUser)", argNames = "joinPoint,currentUser")
	public Object checkIsUserExists(ProceedingJoinPoint joinPoint, AuthenticatedUser currentUser) {
		Mono<Boolean> validation = Mono.justOrEmpty(currentUser.getUserId())
			.switchIfEmpty(Mono.error(new ServerWebInputException("user id is required in jwt")))
			.map(userRepository::existsById)
			.flatMap(mono -> mono)
			.filter(Boolean.TRUE::equals)
			.switchIfEmpty(Mono.error(new ServerWebInputException("user not found")));
		return proceedWithValidation(joinPoint, validation);
	}
}
