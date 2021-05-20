package com.incubator.dbs.guestservice.controller;

import com.incubator.dbs.guestservice.model.dto.LoginRequestDto;
import com.incubator.dbs.guestservice.model.dto.LoginResponseDto;
import com.incubator.dbs.guestservice.model.dto.SignUpRequestDto;
import com.incubator.dbs.guestservice.service.UserDetailServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements UserOperation {

  @Autowired
  private UserDetailServiceImp userDetailServiceImp;

  @Override
  public LoginResponseDto login(LoginRequestDto request) {
    return null;
  }

  @Override
  public String signUp(SignUpRequestDto request) {
    return null;
  }
}
