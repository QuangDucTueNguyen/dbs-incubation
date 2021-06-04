package com.incubator.dbs.reservationservice.intergration;

import com.incubator.dbs.reservationservice.model.dto.UserInfoResponseDTO;
import java.net.URI;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerPort;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@AutoConfigureStubRunner(stubsMode = StubRunnerProperties.StubsMode.LOCAL, ids = "com.incubator.dbs:guest-service")
public class GuestServiceTest {

  private RestTemplate restTemplate;
  private static final UUID guestId = UUID.fromString("182b758f-f490-4187-a7e2-39a7332a13d6");
  private static final String localhost = "http://localhost:";
  @StubRunnerPort("guest-service")
  int port;

  @Before
  public void setup() {
    restTemplate = new RestTemplate();
  }

  @Test
  public void getUser_shouldWork() {
    var uri = URI.create(String.format("%s%s/api/guests/%s", localhost, port, guestId.toString()));
    var rs = restTemplate.exchange(uri, HttpMethod.GET, null, UserInfoResponseDTO.class);
    Assert.assertNotNull(rs.getBody());
    Assert.assertEquals(guestId, rs.getBody().getId());
    Assert.assertEquals("Test_Contract", rs.getBody().getName());
  }
}
