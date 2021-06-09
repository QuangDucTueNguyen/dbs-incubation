package com.incubator.dbs.reservationservice.controller;

import com.incubator.dbs.reservationservice.model.dto.CreateReservationRequestDTO;
import com.incubator.dbs.reservationservice.model.dto.ReservationInfoResponseDTO;
import com.incubator.dbs.reservationservice.model.dto.UpdateReservationRequestDTO;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(value = "/api/reservations")
public interface ReservationOperations {

  @PostMapping
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Create room",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = ReservationInfoResponseDTO.class))}),
      @ApiResponse(responseCode = "400", description = "Invalid value",
          content = @Content)})
  ReservationInfoResponseDTO create(@Valid @RequestBody CreateReservationRequestDTO request);

  @PatchMapping("/{id}")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Update room",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = ReservationInfoResponseDTO.class))}),
      @ApiResponse(responseCode = "400", description = "Invalid value",
          content = @Content),
      @ApiResponse(responseCode = "404", description = "Not Found",
          content = @Content)})
  ReservationInfoResponseDTO update(@PathVariable UUID id, @Valid @RequestBody UpdateReservationRequestDTO request);

  @DeleteMapping("/{id}")
  @ApiResponse(responseCode = "204", description = "No content", content = @Content)
  void delete(@PathVariable UUID id);

  @GetMapping("/users/{id}/history")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Get reservation",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = ReservationInfoResponseDTO.class))}),
      @ApiResponse(responseCode = "404", description = "Not Found",
          content = @Content)})
  List<ReservationInfoResponseDTO> getByUser(@PathVariable String id);

  @GetMapping
  @ApiResponse(responseCode = "200", description = "Get reservation",
      content = {@Content(mediaType = "application/json",
          schema = @Schema(implementation = ReservationInfoResponseDTO.class))})
  List<ReservationInfoResponseDTO> get(@RequestParam Instant from, @RequestParam Instant to);
}
