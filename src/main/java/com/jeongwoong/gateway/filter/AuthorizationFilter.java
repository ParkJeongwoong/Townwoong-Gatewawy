package com.jeongwoong.gateway.filter;

import com.jeongwoong.gateway.execption.UnAuthorizedException;
import com.jeongwoong.gateway.filter.AuthorizationFilter.Config;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class AuthorizationFilter extends AbstractGatewayFilterFactory<Config> {

	@Value("${spring.jwt.secret}")
	private String secretKey;

	public AuthorizationFilter() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			log.info("Authorization Pre Filter Start");
			ServerHttpRequest request = exchange.getRequest();

			if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
				return onError(exchange, "No Authorization header", HttpStatus.UNAUTHORIZED);
			}

			String authorizationHeader = request.getHeaders().getOrEmpty(HttpHeaders.AUTHORIZATION).get(0);
			String token = authorizationHeader.replace("Bearer", "");

			if (!isTokenValid(token)) {
				return onError(exchange, "Invalid Token", HttpStatus.UNAUTHORIZED);
			}
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				log.info("Authorization Post Filter Start");
			}));
		};
	}

	private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(HttpStatus.UNAUTHORIZED);
		log.error("Authorization Filter Error : {}", err);
		return Mono.error(UnAuthorizedException::new);
	}

	private boolean isTokenValid(String token) {
		// Implement JWT token validation logic
		return true;
	}

	@Data
	public static class Config {
		// Put the configuration properties here
	}
}
