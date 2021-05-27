package com.incubator.dbs.guestservice.controller;

import com.incubator.dbs.guestservice.model.dto.GuestInfoResponse;
import com.incubator.dbs.guestservice.model.dto.LoginRequestDto;
import com.incubator.dbs.guestservice.model.dto.LoginResponseDto;
import com.incubator.dbs.guestservice.model.dto.SignUpRequestDto;
import com.incubator.dbs.guestservice.model.dto.SignupResponseDto;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * User operation
 */
@RequestMapping("/api/guests")
public interface UserOperation {

  /**
   * Log in
   * @param request
   * @return
   */
  @PostMapping("/login")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "log in success",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = LoginResponseDto.class))}),
      @ApiResponse(responseCode = "400", description = "Invalid field",
          content = @Content)})
  LoginResponseDto login(LoginRequestDto request);

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
  SignupResponseDto signUp(SignUpRequestDto request);


  /**
   * Get User Info
   * @param id
   * @return
   */
  @GetMapping("/{id}")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Get User info",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = GuestInfoResponse.class))}),
      @ApiResponse(responseCode = "404", description = "Not found",
          content = @Content)})
  GuestInfoResponse get(@PathVariable String id);

  /**
   * delete guest
   * @param id
   */
  @DeleteMapping("/{id}")
  @ApiResponse(responseCode = "204", description = "No content", content = @Content)
  void delete(@PathVariable String id);
}
