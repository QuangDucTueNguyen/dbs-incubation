package com.incubator.dbs.authenticateservice.intergration;


import com.incubator.dbs.authenticateservice.model.dto.CreateUserInfoDto;
import com.incubator.dbs.authenticateservice.model.dto.SignUpRequestDto;
import com.incubator.dbs.authenticateservice.model.dto.UserInfoResponseDto;
import java.net.URI;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerPort;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@AutoConfigureStubRunner(stubsMode = StubRunnerProperties.StubsMode.LOCAL, ids = "com.incubator.dbs:guest-service")
public class GuestServiceTest {

  private final String USERNAME = "USERNAME";
  private final String ADDRESS = "ADDRESS";
  private final String PHONE_NUMBER = "123456789";
  private final String NAME = "NAME";
  private RestTemplate restTemplate;
  private static final String localhost = "http://localhost:";
  @StubRunnerPort("guest-service")
  int port;

  @Before
  public void setUp() {
    restTemplate = new RestTemplate();
  }

  @Test
  public void signUp_shouldWork() throws Exception {
    var request = SignUpRequestDto.builder().name(NAME).address(ADDRESS).phoneNumber(PHONE_NUMBER)
        .username(USERNAME).build();

    var createUserInfoRequest = CreateUserInfoDto.builder()
        .address(request.getAddress())
        .name(request.getName())
        .phoneNumber(request.getPhoneNumber())
        .build();

    HttpHeaders headers = new HttpHeaders();

    var uri = URI.create(String.format("%s%s/api/guests", localhost, port));
    HttpEntity<CreateUserInfoDto> requestEntity = new HttpEntity<>(createUserInfoRequest, headers);
    var rs = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, UserInfoResponseDto.class);
    Assert.assertNotNull(rs.getBody());
  }
}

