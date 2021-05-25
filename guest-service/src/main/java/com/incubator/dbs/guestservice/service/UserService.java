package com.incubator.dbs.guestservice.service;

import com.incubator.dbs.guestservice.model.dto.GuestInfoResponse;
import com.incubator.dbs.guestservice.model.dto.LoginRequestDto;
import com.incubator.dbs.guestservice.model.dto.LoginResponseDto;
import com.incubator.dbs.guestservice.model.dto.SignUpRequestDto;
import com.incubator.dbs.guestservice.model.dto.SignupResponseDto;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.UUID;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

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
  GuestInfoResponse get(String id);

  /**
   * delete guest
   * @param id
   */
  void delete(String id);
}
