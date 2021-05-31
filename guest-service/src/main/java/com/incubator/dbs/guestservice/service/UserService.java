package com.incubator.dbs.guestservice.service;

import com.incubator.dbs.guestservice.model.dto.GuestInfoResponseDto;
import com.incubator.dbs.guestservice.model.dto.CreateGuestRequestDto;
import java.util.UUID;

/**
 * User service
 */
public interface UserService {

  /**
   * create user profile
   * @param request
   * @return
   */
  GuestInfoResponseDto create(CreateGuestRequestDto request);

  /**
   * Get User Info
   * @param id
   * @return
   */
  GuestInfoResponseDto get(UUID id);

  /**
   * delete guest
   * @param id
   */
  void delete(UUID id);
}
