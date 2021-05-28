package com.incubator.dbs.guestservice.service;

import com.incubator.dbs.guestservice.model.dto.UserCredentialDto;

public interface UserCredential {
  UserCredentialDto loadUserByUsername(String s);
}
