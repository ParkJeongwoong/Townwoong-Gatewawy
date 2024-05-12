package com.jeongwoong.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class GlobalTokenHeaderFilter implements GlobalFilter, Ordered {

	/*
	이건 application.yml에 추가 안 해도 동작 (GlobalFilter 를 상속받기 때문)
	 */

	@Bean
	public GlobalTokenHeaderFilter globalFilter() {
		return new GlobalTokenHeaderFilter();
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		exchange.getRequest().mutate().header("X-Gateway-Header", generateToken());
		return chain.filter(exchange);
	}

	@Override
	public int getOrder() {
		return -1;
	}

	private String generateToken() {
		// Implement JWT token generation logic
		return "test-token";
	}

}
