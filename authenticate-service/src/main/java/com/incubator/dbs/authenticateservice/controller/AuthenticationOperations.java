package com.incubator.dbs.authenticateservice.controller;

import com.incubator.dbs.authenticateservice.model.dto.LoginRequestDto;
import com.incubator.dbs.authenticateservice.model.dto.LoginResponseDto;
import com.incubator.dbs.authenticateservice.model.dto.SignUpRequestDto;
import com.incubator.dbs.authenticateservice.model.dto.SignupResponseDto;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/api/auth")
public interface AuthenticationOperations {


  /**
   * Log in
   * @param request
   * @return
   */
  @PostMapping
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "log in success",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = LoginResponseDto.class))}),
      @ApiResponse(responseCode = "400", description = "Invalid field",
          content = @Content)})
  LoginResponseDto login(@RequestBody LoginRequestDto request);

  /**
   * Sign up
   * @param request
   * @return
   */
  @PostMapping("/signup")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Create user",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = SignupResponseDto.class))}),
      @ApiResponse(responseCode = "400", description = "Invalid field",
          content = @Content)})
  SignupResponseDto signUp(@RequestBody SignUpRequestDto request);
}
