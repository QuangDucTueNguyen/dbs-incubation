package com.incubator.dbs.reservationservice.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.incubator.dbs.reservationservice.model.constant.ReservationStatus;
import com.incubator.dbs.reservationservice.model.dto.CreateReservationRequestDTO;
import com.incubator.dbs.reservationservice.model.dto.ReservationInfoResponseDTO;
import com.incubator.dbs.reservationservice.model.dto.UpdateReservationRequestDTO;
import com.incubator.dbs.reservationservice.service.ReservationService;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
public class ReservationControllerTest {

  private ReservationService reservationService;
  private ReservationController reservationController;
  private static final Instant from = Instant.now();
  private static final Instant to = from.plusMillis(5 * 24 * 60 * 60 * 100);
  private static final UUID USER_ID = UUID.randomUUID();
  private static final UUID RESERVATION_ID_1 = UUID.randomUUID();
  private static final UUID RESERVATION_ID_2 = UUID.randomUUID();
  private static final Integer HOTEL_ID_1 = RandomUtils.nextInt();
  private static final Integer HOTEL_ID_2 = RandomUtils.nextInt();
  private static final Integer ROOM_TYPE_ID_1 = RandomUtils.nextInt();
  private static final Integer ROOM_TYPE_ID_2 = RandomUtils.nextInt();
  private static final Integer NUMBER_ROOMS = 5;
  private static final Double TOTAL = RandomUtils.nextDouble();
  private ObjectMapper mapper;

  private MockMvc mockMvc;

  @Before
  public void setup() {
    mapper = new ObjectMapper();
    JavaTimeModule module = new JavaTimeModule();
    mapper.registerModule(module);
    reservationService = Mockito.mock(ReservationService.class);
    reservationController = new ReservationController(reservationService);
    mockMvc = standaloneSetup(reservationController).build();
  }

  @Test
  public void create_shouldWork() throws Exception {
    var request = CreateReservationRequestDTO.builder()
        .from(from)
        .to(to)
        .hotelId(HOTEL_ID_1)
        .numberRooms(NUMBER_ROOMS)
        .roomTypeId(ROOM_TYPE_ID_1)
        .userId(USER_ID.toString())
        .build();
    var expected = ReservationInfoResponseDTO.builder()
        .status(ReservationStatus.PENDING)
        .from(from)
        .to(to)
        .numberRooms(NUMBER_ROOMS)
        .hotelId(HOTEL_ID_1)
        .roomTypeId(ROOM_TYPE_ID_1)
        .id(RESERVATION_ID_1)
        .total(TOTAL)
        .build();
    Mockito.when(reservationService.create(request)).thenReturn(expected);
    mockMvc.perform(
        MockMvcRequestBuilders.post("/api/reservations")
            .content(this.mapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(content().string(this.mapper.writeValueAsString(expected)));
  }

  @Test
  public void update_shouldWork() throws Exception {
    var id = RESERVATION_ID_1;
    var request = UpdateReservationRequestDTO.builder()
        .status(ReservationStatus.PAID.getValue())
        .build();
    var expected = ReservationInfoResponseDTO.builder()
        .id(id)
        .status(ReservationStatus.PAID)
        .build();
    Mockito.when(reservationService.update(id.toString(), request)).thenReturn(expected);
    mockMvc.perform(
        MockMvcRequestBuilders.patch(String.format("/api/reservations/%s", id.toString()))
            .content(this.mapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(this.mapper.writeValueAsString(expected)));
  }

  @Test
  public void delete_shouldWork() throws Exception {
    var id = RESERVATION_ID_1.toString();
    reservationController.delete(id);
    mockMvc.perform(
        MockMvcRequestBuilders.delete(String.format("/api/reservations/%s", id))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }

  @Test
  public void getByUser_shouldWork() throws Exception {
    var reservation1 = ReservationInfoResponseDTO.builder()
        .hotelId(HOTEL_ID_1)
        .roomTypeId(ROOM_TYPE_ID_1)
        .id(RESERVATION_ID_1)
        .build();
    var reservation2 = ReservationInfoResponseDTO.builder()
        .hotelId(HOTEL_ID_2)
        .roomTypeId(ROOM_TYPE_ID_2)
        .id(RESERVATION_ID_2)
        .build();
    var expected = List.of(reservation1, reservation2);
    Mockito.when(reservationService.getByUser(USER_ID.toString())).thenReturn(expected);

    mockMvc.perform(
        MockMvcRequestBuilders.get(String.format("/api/reservations/users/%s/history", USER_ID.toString()))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(this.mapper.writeValueAsString(expected)));
  }

  @Test
  public void get_shouldWork() throws Exception {
    var reservation1 = ReservationInfoResponseDTO.builder()
        .from(from)
        .to(to)
        .hotelId(HOTEL_ID_1)
        .numberRooms(NUMBER_ROOMS)
        .roomTypeId(ROOM_TYPE_ID_1)
        .id(RESERVATION_ID_1)
        .build();
    var reservation2 = ReservationInfoResponseDTO.builder()
        .from(from)
        .to(to)
        .hotelId(HOTEL_ID_2)
        .numberRooms(NUMBER_ROOMS)
        .roomTypeId(ROOM_TYPE_ID_2)
        .id(RESERVATION_ID_2)
        .build();
    var expected = List.of(reservation1, reservation2);
    Mockito.when(reservationService.get(from, to)).thenReturn(expected);
    mockMvc.perform(
        MockMvcRequestBuilders.get(String.format("/api/reservations"))
            .param("from", from.toString())
            .param("to", to.toString())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(this.mapper.writeValueAsString(expected)));
  }
}
