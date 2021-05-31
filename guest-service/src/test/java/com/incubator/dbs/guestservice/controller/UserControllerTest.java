package com.incubator.dbs.guestservice.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.incubator.dbs.guestservice.model.dto.GuestInfoResponseDto;
import com.incubator.dbs.guestservice.model.dto.CreateGuestRequestDto;
import com.incubator.dbs.guestservice.service.UserService;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTest {

  private UserOperation userOperation;
  private UserService userService;
  private final String USERNAME = "USERNAME";
  private final String PASSWORD = "PASSWORD";
  private final String DEFAULT_PASSWORD = "PASSWORD";
  private final String ADDRESS = "ADDRESS";
  private final String PHONE_NUMBER = "+8412345678";
  private final String NAME = "NAME";
  private final String ACCESS_TOKEN = "ACCESS_TOKEN";
  private final UUID USER_ID = UUID.randomUUID();
  private ObjectMapper mapper;

  private MockMvc mockMvc;

  public UserControllerTest() {
    mapper = new ObjectMapper();
    JavaTimeModule module = new JavaTimeModule();
    mapper.registerModule(module);
    userService = Mockito.mock(UserService.class);
    userOperation = new UserController(userService);
    mockMvc = standaloneSetup(userOperation).build();
  }

  @Test
  public void create_shouldWork() throws Exception {
    var request = CreateGuestRequestDto.builder()
        .name(NAME)
        .address(ADDRESS)
        .phoneNumber(PHONE_NUMBER)
        .build();
    var expected = GuestInfoResponseDto.builder()
        .address(request.getAddress())
        .name(request.getName())
        .phoneNumber(request.getPhoneNumber())
        .build();
    Mockito.when(userService.create(request)).thenReturn(expected);
    mockMvc.perform(
        MockMvcRequestBuilders.post("/api/guests")
            .content(this.mapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(content().string(this.mapper.writeValueAsString(expected)));
  }


  @Test
  public void delete_shouldWork() throws Exception {
    mockMvc.perform(
        MockMvcRequestBuilders.delete(String.format("/api/guests/%s", USER_ID.toString()))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }

  @Test
  public void get_shouldWork() throws Exception {
    var expected = GuestInfoResponseDto.builder()
        .id(USER_ID)
        .phoneNumber(PHONE_NUMBER)
        .build();
    Mockito.when(userService.get(USER_ID)).thenReturn(expected);
    mockMvc.perform(
        MockMvcRequestBuilders.get(String.format("/api/guests/%s", USER_ID.toString()))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(this.mapper.writeValueAsString(expected)));
  }
}
