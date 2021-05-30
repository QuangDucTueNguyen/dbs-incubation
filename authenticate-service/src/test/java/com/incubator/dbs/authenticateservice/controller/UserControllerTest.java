package com.incubator.dbs.authenticateservice.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.incubator.dbs.authenticateservice.model.dto.LoginRequestDto;
import com.incubator.dbs.authenticateservice.model.dto.LoginResponseDto;
import com.incubator.dbs.authenticateservice.model.dto.SignUpRequestDto;
import com.incubator.dbs.authenticateservice.model.dto.SignupResponseDto;
import com.incubator.dbs.authenticateservice.service.AuthenticationService;
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

  private AuthenticationOperations authenticationOperations;
  private AuthenticationService authenticationService;
  private final String USERNAME = "USERNAME";
  private final String PASSWORD = "PASSWORD";
  private final String DEFAULT_PASSWORD = "PASSWORD";
  private final String ADDRESS = "ADDRESS";
  private final String PHONE_NUMBER = "+8412345678";
  private final String NAME = "NAME";
  private final String ACCESS_TOKEN = "ACCESS_TOKEN";
  private ObjectMapper mapper;

  private MockMvc mockMvc;

  public UserControllerTest() {
    mapper = new ObjectMapper();
    JavaTimeModule module = new JavaTimeModule();
    mapper.registerModule(module);
    authenticationService = Mockito.mock(AuthenticationService.class);
    authenticationOperations = new AuthenticationController(authenticationService);
    mockMvc = standaloneSetup(authenticationOperations).build();
  }

  @Test
  public void signUp_shouldWork() throws Exception {
    var signUpRequest = SignUpRequestDto.builder().name(NAME).address(ADDRESS).phoneNumber(PHONE_NUMBER)
        .username(USERNAME).build();
    var expected = SignupResponseDto.builder().defaultPassword(DEFAULT_PASSWORD).build();
    Mockito.when(authenticationService.signUp(signUpRequest)).thenReturn(expected);
    mockMvc.perform(
        MockMvcRequestBuilders.post("/api/auth/signup")
            .content(this.mapper.writeValueAsString(signUpRequest))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(content().string(this.mapper.writeValueAsString(expected)));
  }

  @Test
  public void login_shouldWork() throws Exception {
    var loginRequest = LoginRequestDto.builder().username(USERNAME).password(PASSWORD).build();
    var expected = LoginResponseDto.builder().accessToken(ACCESS_TOKEN).build();
    Mockito.when(authenticationService.login(loginRequest)).thenReturn(expected);
    mockMvc.perform(
        MockMvcRequestBuilders.post("/api/auth")
            .content(this.mapper.writeValueAsString(loginRequest))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(this.mapper.writeValueAsString(expected)));
  }

}
