package com.incubator.dbs.guestservice.controller;

import com.incubator.dbs.guestservice.model.dto.LoginRequestDto;
import com.incubator.dbs.guestservice.model.dto.LoginResponseDto;
import com.incubator.dbs.guestservice.model.dto.SignUpRequestDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/guest")
public interface UserOperation {

  @PostMapping("/login")
  LoginResponseDto login(LoginRequestDto request);

  @PostMapping("/signup")
  String signUp(SignUpRequestDto request);

}
