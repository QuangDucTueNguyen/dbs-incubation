package com.incubator.dbs.gateway.configuration;

import com.incubator.dbs.gateway.util.JwtUtil;
import io.jsonwebtoken.Claims;
import java.util.Arrays;
import java.util.function.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
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
public class Filter implements GatewayFilter {

  @Autowired
  private JwtUtil jwtUtil;

  @Value("${spring.cloud.gateway.ignorePatterns}")
  private String[] IGNORE_PATH;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    ServerHttpRequest request = exchange.getRequest();
    final String token = this.getAuthHeader(request);
    if (isSecureApi.test(request)) {
      if (this.isTokenValid(request, token)) {
        log.error("Token is invalid");
        return this.onError(exchange, "Authorization header is invalid", HttpStatus.UNAUTHORIZED);
      }
      return this.populateRequestWithHeaders(exchange, token);
    }
    return chain.filter(exchange);
  }

  private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
    ServerHttpResponse response = exchange.getResponse();
    response.setStatusCode(httpStatus);
    return response.setComplete();
  }

  private String getAuthHeader(ServerHttpRequest request) {
    return request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
  }

  private Boolean isTokenValid(ServerHttpRequest request, String token) {
    return request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION) && jwtUtil.isInvalid(token);
  }

  private Mono<Void> populateRequestWithHeaders(ServerWebExchange exchange, String token) {
    return Mono.just(jwtUtil.getAllClaimsFromToken(token))
        .map(claims -> exchange.getRequest().mutate())
        .then();
  }

  private Predicate<ServerHttpRequest> isSecureApi =
      request -> Arrays.stream(IGNORE_PATH).noneMatch(uri -> request.getURI().getPath().contains(uri));
}
