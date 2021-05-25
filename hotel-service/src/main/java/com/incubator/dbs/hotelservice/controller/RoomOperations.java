package com.incubator.dbs.hotelservice.controller;

import com.incubator.dbs.hotelservice.model.dto.CreateRoomRequest;
import com.incubator.dbs.hotelservice.model.dto.RoomInfoResponse;
import com.incubator.dbs.hotelservice.model.dto.RoomTypeResponse;
import com.incubator.dbs.hotelservice.model.dto.UpdateRoomRequest;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.time.Instant;
import java.util.List;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Manipulate room information
 */
@RequestMapping(value = "/api/rooms")
public interface RoomOperations {

  /**
   * get all room type
   *
   * @return
   */
  @GetMapping(value = "/types")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Get specific room in hotel",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = RoomTypeResponse.class))}),
      @ApiResponse(responseCode = "404", description = "Not found",
          content = @Content)})
  List<RoomTypeResponse> getAllRoomType();

  /**
   * create room
   *
   * @param createRoomRequest
   * @return
   */
  @PostMapping
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Create room",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = RoomInfoResponse.class))}),
      @ApiResponse(responseCode = "400", description = "Invalid value",
          content = @Content)})
  RoomInfoResponse createRoom(@Valid @RequestBody CreateRoomRequest createRoomRequest);

  /**
   * update room
   *
   * @param id
   * @param updateRoomRequest
   * @return
   */
  @PatchMapping(value = "/{id}")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Update room",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = RoomInfoResponse.class))}),
      @ApiResponse(responseCode = "400", description = "Invalid value",
          content = @Content)})
  RoomInfoResponse updateRoom(@PathVariable Integer id, @RequestBody UpdateRoomRequest updateRoomRequest);

  /**
   * get all room by type and specific arrange time
   *
   * @param from
   * @param to
   * @return
   */
  @GetMapping
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Get all room",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = RoomInfoResponse.class))}),
      @ApiResponse(responseCode = "400", description = "Invalid value",
          content = @Content)})
  List<RoomTypeResponse> getAllRoomInArrangeTime(@RequestParam Instant from, @RequestParam Instant to);
}
