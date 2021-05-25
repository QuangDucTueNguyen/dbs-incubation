package com.incubator.dbs.hotelservice.controller;


import com.incubator.dbs.hotelservice.model.dto.CreateHotelRequest;
import com.incubator.dbs.hotelservice.model.dto.CreateHotelResponse;
import com.incubator.dbs.hotelservice.model.dto.RoomInfoResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Manipulate hotel
 */
@RequestMapping("/api/hotels")
public interface HotelOperations {

  /**
   * Get room by hotel
   * @param hotelId
   * @param roomId
   * @return
   */
  @GetMapping(value = "api/hotels/{hotelId}/rooms/{roomId}")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Get specific room in hotel",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = RoomInfoResponse.class))}),
      @ApiResponse(responseCode = "400", description = "Invalid field",
          content = @Content)})
  RoomInfoResponse getRoom(@PathVariable Integer hotelId, @PathVariable Integer roomId);

  /**
   * create a hotel
   * @param createHotelRequest
   * @return
   */
  @PostMapping
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Create a hotel",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = CreateHotelResponse.class))}),
      @ApiResponse(responseCode = "400", description = "Invalid field",
          content = @Content)})
  CreateHotelResponse createHotel(@Valid @RequestBody CreateHotelRequest createHotelRequest);
}
