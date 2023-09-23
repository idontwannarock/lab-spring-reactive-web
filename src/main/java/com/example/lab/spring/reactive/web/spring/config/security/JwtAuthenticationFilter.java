package com.example.lab.spring.reactive.web.spring.config.security;

import com.example.lab.spring.reactive.web.spring.config.security.dto.AuthenticatedUser;
import com.example.lab.spring.reactive.web.spring.config.security.exception.InvalidTokenException;
import com.example.lab.spring.reactive.web.spring.config.security.service.TokenUserService;
import com.example.lab.spring.reactive.web.spring.config.security.service.UserAuthorityService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SecurityException;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.io.Serial;
import java.util.Collection;

import static com.example.lab.spring.reactive.web.spring.config.security.exception.ErrorCode.*;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter implements WebFilter {

	private final TokenUserService tokenUserService;
	private final UserAuthorityService userAuthorityService;
	private final ServerAuthenticationEntryPoint authenticationEntryPoint;

	@NonNull
	@Override
	public Mono<Void> filter(@NonNull ServerWebExchange exchange, @NonNull WebFilterChain chain) {
		if (shouldNotFilter(exchange)) {
			return chain.filter(exchange);
		}
		String header = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		if (header == null || header.isBlank()) {
			return chain.filter(exchange);
		}
		try {
			String token = header.substring(7);
			return chain.filter(exchange)
				.contextWrite(ReactiveSecurityContextHolder.withAuthentication(asAuthentication(token)));
		} catch (InvalidTokenException e) {
			// then the request won't pass through the AuthenticationManager or ControllerAdvice
			return authenticationEntryPoint.commence(exchange, e);
		}
	}

	private static boolean shouldNotFilter(ServerWebExchange exchange) {
		HttpHeaders headers = exchange.getRequest().getHeaders();
		return headers.isEmpty() || !headers.containsKey(HttpHeaders.AUTHORIZATION);
	}

	private Authentication asAuthentication(String token) {
		try {
			AuthenticatedUser user = tokenUserService.toUser(token);
			Collection<? extends GrantedAuthority> authorities = userAuthorityService.getAuthorities(user);
			return new AuthenticatedUserToken(user, token, authorities);
		} catch (ExpiredJwtException e) {
			throw new InvalidTokenException(EXPIRED_JWT_TOKEN, "Expired JWT token");
		} catch (UnsupportedJwtException e) {
			throw new InvalidTokenException(UNSUPPORTED_JWT_TOKEN, "Unsupported JWT token");
		} catch (MalformedJwtException e) {
			throw new InvalidTokenException(INVALID_JWT_TOKEN, "Invalid JWT token");
		} catch (SecurityException e) {
			throw new InvalidTokenException(INVALID_JWT_SIGNATURE, "JWT signature is invalid");
		} catch (IllegalArgumentException e) {
			throw new InvalidTokenException(INVALID_JWT_COMPACT_HANDLER, "JWT token compact of handler is invalid");
		} catch (Exception e) {
			throw new InvalidTokenException(UNEXPECTED_JWT_TOKEN_ERROR, e.getMessage());
		}
	}

	@EqualsAndHashCode(callSuper = true)
	public static class AuthenticatedUserToken extends AbstractAuthenticationToken {

		@Serial
		private static final long serialVersionUID = 826805289479390910L;

		private final AuthenticatedUser principal;

		private String credentials;

		/**
		 * Creates a token with the supplied array of authorities.
		 *
		 * @param authorities the collection of <tt>GrantedAuthority</tt>s for the principal
		 *                    represented by this authentication object.
		 */
		public AuthenticatedUserToken(AuthenticatedUser principal, String credentials, Collection<? extends GrantedAuthority> authorities) {
			super(authorities);
			this.principal = principal;
			this.credentials = credentials;
			super.setAuthenticated(true); // must use super, as we override
		}

		@Override
		public AuthenticatedUser getPrincipal() {
			return principal;
		}

		@Override
		public String getCredentials() {
			return credentials;
		}

		@Override
		public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
			Assert.isTrue(!isAuthenticated,
				"Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
			super.setAuthenticated(false);
		}

		@Override
		public void eraseCredentials() {
			super.eraseCredentials();
			this.credentials = null;
		}
	}
}
