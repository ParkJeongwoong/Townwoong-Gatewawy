package com.jeongwoong.gateway.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {

	public CustomFilter() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();
			ServerHttpResponse response = exchange.getResponse();

			log.info("Custom Pre Filter : request id -> {}", request.getId());
			log.info("Custom Pre Filter : request path -> {}", request.getPath());

			String modifiedPath = request.getURI().getPath().replaceFirst("/member", "/api");
			exchange = exchange.mutate().request(request.mutate().path(modifiedPath).build()).build();

			log.info("Custom Pre Filter : modified path -> {}", exchange.getRequest().getPath());

			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				log.info("Custom Post Filter : response code -> {}", response.getStatusCode());
			}));
		};
	}

	@Data
	public static class Config {
		// Put the configuration properties here
	}

}
