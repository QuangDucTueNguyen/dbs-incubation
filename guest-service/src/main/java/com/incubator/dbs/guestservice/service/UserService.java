package com.incubator.dbs.guestservice.service;

import com.incubator.dbs.guestservice.model.dto.GuestInfoResponseDto;
import com.incubator.dbs.guestservice.model.dto.LoginRequestDto;
import com.incubator.dbs.guestservice.model.dto.LoginResponseDto;
import com.incubator.dbs.guestservice.model.dto.SignUpRequestDto;
import com.incubator.dbs.guestservice.model.dto.SignupResponseDto;

/**
 * User service
 */
public interface UserService {

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

  /**
   * Get User Info
   * @param id
   * @return
   */
  GuestInfoResponseDto get(String id);

  /**
   * delete guest
   * @param id
   */
  void delete(String id);
}
