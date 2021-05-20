package com.incubator.dbs.guestservice.service;

import com.incubator.dbs.guestservice.model.dto.LoginRequestDto;
import com.incubator.dbs.guestservice.model.dto.LoginResponseDto;
import com.incubator.dbs.guestservice.model.dto.SignUpRequestDto;

public interface UserService {

  LoginResponseDto login(LoginRequestDto request);

  String signUp(SignUpRequestDto request);
}
