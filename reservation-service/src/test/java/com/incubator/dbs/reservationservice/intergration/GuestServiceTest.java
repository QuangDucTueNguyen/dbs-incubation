package com.incubator.dbs.reservationservice.intergration;

import com.incubator.dbs.reservationservice.service.connector.GuestServiceConnector;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureStubRunner(stubsMode = StubRunnerProperties.StubsMode.LOCAL,
    ids = "com.incubator.dbs:guest-service:+:stubs:8095")
@EnableFeignClients(clients = GuestServiceConnector.class)
@Import({FeignAutoConfiguration.class, HttpMessageConvertersAutoConfiguration.class})
@TestPropertySource(properties = {
    "guestService.host=http://localhost:8095",
    "guestService.getUserInfo=/api/guests/{id}"
})
public class GuestServiceTest {

  private static final UUID guestId = UUID.fromString("182b758f-f490-4187-a7e2-39a7332a13d6");
  @Autowired
  private GuestServiceConnector guestServiceConnector;
  @Test
  public void getUser_shouldWork() {
    var rs = guestServiceConnector.getUserInfo(guestId.toString());
    Assert.assertEquals(guestId, rs.get().getId());
    Assert.assertEquals("Test_Contract", rs.get().getName());
  }
}
