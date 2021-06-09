package com.incubator.dbs.authenticateservice.intergration;


import com.incubator.dbs.authenticateservice.model.dto.CreateUserInfoDto;
import com.incubator.dbs.authenticateservice.model.dto.SignUpRequestDto;
import com.incubator.dbs.authenticateservice.model.dto.UserInfoResponseDto;
import com.incubator.dbs.authenticateservice.service.connector.GuestServiceConnector;
import java.net.URI;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerPort;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureStubRunner(stubsMode = StubRunnerProperties.StubsMode.LOCAL,
    ids = "com.incubator.dbs:guest-service:+:stubs:8095")
@EnableFeignClients(clients = GuestServiceConnector.class)
@Import({FeignAutoConfiguration.class, HttpMessageConvertersAutoConfiguration.class})
@TestPropertySource(properties = {
    "guestService.host=http://localhost:8095",
    "guestService.createUserProfile=/api/guests"
})
public class GuestServiceTest {

  private final String USERNAME = "USERNAME";
  private final String ADDRESS = "ADDRESS";
  private final String PHONE_NUMBER = "123456789";
  private final String NAME = "NAME";
  @Autowired
  private GuestServiceConnector guestServiceConnector;

  @Test
  public void signUp_shouldWork() {
    var request = SignUpRequestDto.builder().name(NAME).address(ADDRESS).phoneNumber(PHONE_NUMBER)
        .username(USERNAME).build();
    var createUserInfoRequest = CreateUserInfoDto.builder()
        .address(request.getAddress())
        .name(request.getName())
        .phoneNumber(request.getPhoneNumber())
        .build();
    var rs = guestServiceConnector.create(createUserInfoRequest);
    Assert.assertNotNull(rs);
  }
}

