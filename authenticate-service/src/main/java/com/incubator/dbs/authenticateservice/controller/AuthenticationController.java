package com.incubator.dbs.authenticateservice.controller;

import com.incubator.dbs.authenticateservice.model.dto.LoginRequestDto;
import com.incubator.dbs.authenticateservice.model.dto.LoginResponseDto;
import com.incubator.dbs.authenticateservice.model.dto.SignUpRequestDto;
import com.incubator.dbs.authenticateservice.model.dto.SignupResponseDto;
import com.incubator.dbs.authenticateservice.service.AuthenticationService;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CircuitBreaker(name = "auth")
@Retry(name = "auth")
@RateLimiter(name = "auth")
@TimeLimiter(name = "auth")
@Bulkhead(name = "auth")
public class AuthenticationController implements AuthenticationOperations {

  private final AuthenticationService authenticationService;

  public AuthenticationController(
      AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @Override
  public LoginResponseDto login(LoginRequestDto request) {
    return authenticationService.login(request);
  }

  @Override
  @ResponseStatus(code = HttpStatus.CREATED)
  public SignupResponseDto signUp(SignUpRequestDto request) {
    return authenticationService.signUp(request);
  }
}
