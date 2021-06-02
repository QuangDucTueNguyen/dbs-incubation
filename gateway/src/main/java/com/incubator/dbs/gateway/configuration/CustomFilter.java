package com.incubator.dbs.gateway.configuration;

import com.incubator.dbs.gateway.util.JwtUtil;
import java.util.Arrays;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RefreshScope
@Component
@Slf4j
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {

  @Autowired
  private JwtUtil jwtUtil;

  public CustomFilter() {
    super(Config.class);
  }

  @Override
  public GatewayFilter apply(Config config) {
    return ((exchange, chain) -> {
      var request = exchange.getRequest();
      final String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
      if (isSecureApi(request, config.getIgnorePaths())) {
        if (!this.isTokenValid(request, token)) {
          log.error("Token is invalid");
          return this.onError(exchange, HttpStatus.UNAUTHORIZED);
        }
        exchange.getRequest().mutate().build();
      }
      return chain.filter(exchange);
    });
  }

  private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
    ServerHttpResponse response = exchange.getResponse();
    response.setStatusCode(httpStatus);
    return response.setComplete();
  }


  private boolean isTokenValid(ServerHttpRequest request, String token) {
    return request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION) && jwtUtil.isInvalid(token);
  }


  private Boolean isSecureApi(ServerHttpRequest request, String[] ignore) {
    return Arrays.stream(ignore).noneMatch(uri -> request.getURI().getPath().contains(uri));
  }

  @Data
  public static class Config {

    private String[] ignorePaths = {"/api/auth/**"};
  }

}
