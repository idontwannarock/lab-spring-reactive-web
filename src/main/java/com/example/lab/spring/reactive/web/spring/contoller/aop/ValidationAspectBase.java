package com.example.lab.spring.reactive.web.spring.contoller.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.reactivestreams.Publisher;
import org.springframework.core.ReactiveAdapter;
import org.springframework.core.ReactiveAdapterRegistry;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class ValidationAspectBase {

	@SuppressWarnings("java:S2583")
	protected Object proceedWithValidation(ProceedingJoinPoint joinPoint, Mono<?> validation) {
		Signature signature = joinPoint.getSignature();
		Class<?> returnType = ((MethodSignature) signature).getReturnType();
		ReactiveAdapter adapter = ReactiveAdapterRegistry.getSharedInstance().getAdapter(returnType);
		if (isMultiValue(returnType, adapter)) {
			Publisher<?> publisher = Flux.defer(() -> proceed(joinPoint));
			Flux<?> result = validation.thenMany(publisher);
			return (adapter != null) ? adapter.fromPublisher(result) : result;
		}
		Mono<?> publisher = Mono.defer(() -> proceed(joinPoint));
		Mono<?> result = validation.then(publisher);
		return (adapter != null) ? adapter.fromPublisher(result) : result;
	}

	private boolean isMultiValue(Class<?> returnType, ReactiveAdapter adapter) {
		if (Flux.class.isAssignableFrom(returnType)) {
			return true;
		}
		return adapter == null || adapter.isMultiValue();
	}

	protected <T> T proceed(ProceedingJoinPoint joinPoint) {
		try {
			return (T) joinPoint.proceed();
		}
		catch (Throwable ex) {
			throw Exceptions.propagate(ex);
		}
	}
}
