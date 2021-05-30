package com.incubator.dbs.authenticateservice.service;

import com.incubator.dbs.authenticateservice.model.dto.LoginRequestDto;
import com.incubator.dbs.authenticateservice.model.dto.LoginResponseDto;
import com.incubator.dbs.authenticateservice.model.dto.SignUpRequestDto;
import com.incubator.dbs.authenticateservice.model.dto.SignupResponseDto;

public interface AuthenticationService {

  /**
   * Log in
   * @param request
   * @return
   */
  LoginResponseDto login(LoginRequestDto request);

  /**
   * Signup
   * @param request
   * @return
   */
  SignupResponseDto signUp(SignUpRequestDto request);

}
