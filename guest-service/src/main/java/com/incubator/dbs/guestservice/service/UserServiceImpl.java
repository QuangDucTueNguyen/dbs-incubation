package com.incubator.dbs.guestservice.service;

import com.incubator.dbs.guestservice.exception.GuestErrorResponse;
import com.incubator.dbs.guestservice.exception.GuestServiceException;
import com.incubator.dbs.guestservice.model.dto.LoginRequestDto;
import com.incubator.dbs.guestservice.model.dto.LoginResponseDto;
import com.incubator.dbs.guestservice.model.dto.SignUpRequestDto;
import com.incubator.dbs.guestservice.utility.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


  @Autowired
  private JWTUtility jwtUtility;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserDetailsService userDetailsService;

  @Override
  public LoginResponseDto login(LoginRequestDto request) {
    try {
      var up = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
      authenticationManager.authenticate(up);
    } catch (BadCredentialsException e) {
      throw new GuestServiceException(GuestErrorResponse.NOT_AUTHORIZED);
    }
    final var userDetails = userDetailsService.loadUserByUsername(request.getUsername());

    final String token = jwtUtility.generateToken(userDetails);

    return LoginResponseDto.builder().accessToken(token).build();
  }

  @Override
  public String signUp(SignUpRequestDto request) {
    return null;
  }
}
