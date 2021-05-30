package com.incubator.dbs.guestservice.controller;

import com.incubator.dbs.guestservice.model.dto.GuestInfoResponseDto;
import com.incubator.dbs.guestservice.model.dto.CreateGuestRequestDto;
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
public interface GuestsOperation {

  /**
   * Register user info
   * @param request
   * @return
   */
  @PostMapping
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Create user",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = GuestInfoResponseDto.class))}),
      @ApiResponse(responseCode = "400", description = "Invalid field",
          content = @Content)})
  GuestInfoResponseDto signUp(CreateGuestRequestDto request);


  /**
   * Get User Info
   * @param id
   * @return
   */
  @GetMapping("/{id}")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Get User info",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = GuestInfoResponseDto.class))}),
      @ApiResponse(responseCode = "404", description = "Not found",
          content = @Content)})
  GuestInfoResponseDto get(@PathVariable String id);

  /**
   * delete guest
   * @param id
   */
  @DeleteMapping("/{id}")
  @ApiResponse(responseCode = "204", description = "No content", content = @Content)
  void delete(@PathVariable String id);
}
