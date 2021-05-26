package com.incubator.dbs.reservationservice.controller;

import com.incubator.dbs.reservationservice.model.dto.CreateReservationRequest;
import com.incubator.dbs.reservationservice.model.dto.ReservationInfoResponse;
import com.incubator.dbs.reservationservice.model.dto.UpdateReservationRequest;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.time.Instant;
import java.util.List;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(value = "/api/reservation")
public interface ReservationOperations {

  @PostMapping
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Create room",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = ReservationInfoResponse.class))}),
      @ApiResponse(responseCode = "400", description = "Invalid value",
          content = @Content)})
  ReservationInfoResponse create(@Valid @RequestBody CreateReservationRequest request);

  @PatchMapping("/{id}")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Update room",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = ReservationInfoResponse.class))}),
      @ApiResponse(responseCode = "400", description = "Invalid value",
          content = @Content),
      @ApiResponse(responseCode = "404", description = "Not Found",
          content = @Content)})
  ReservationInfoResponse update(@PathVariable String id, @Valid @RequestBody UpdateReservationRequest request);

  @DeleteMapping("/{id}")
  @ApiResponse(responseCode = "204", description = "No content", content = @Content)
  void delete(@PathVariable String id);

  @GetMapping("/users/{id}/history")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Get reservation",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = ReservationInfoResponse.class))}),
      @ApiResponse(responseCode = "404", description = "Not Found",
          content = @Content)})
  List<ReservationInfoResponse> getByUser(@PathVariable String id);

  @GetMapping
  @ApiResponse(responseCode = "200", description = "Get reservation",
      content = {@Content(mediaType = "application/json",
          schema = @Schema(implementation = ReservationInfoResponse.class))})
  List<ReservationInfoResponse> get(@RequestParam Instant from, @RequestParam Instant to);
}
