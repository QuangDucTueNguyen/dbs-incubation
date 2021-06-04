package com.incubator.dbs.hotelservice.intergration;

import com.incubator.dbs.hotelservice.model.dto.ReservationResponse;
import java.net.URI;
import java.util.Arrays;
import org.apache.commons.lang3.math.NumberUtils;
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
@AutoConfigureStubRunner(stubsMode = StubRunnerProperties.StubsMode.LOCAL, ids = "com.incubator.dbs.reservationservice:reservation-service")
public class ReservationServiceTest {

  private RestTemplate restTemplate;
  private static final String localhost = "http://localhost:";
  @StubRunnerPort("reservation-service")
  int port;

  @Before
  public void setup() {
    restTemplate = new RestTemplate();
  }

  @Test
  public void getAllReservation_shouldWork() {
    var uri = URI.create(String
        .format("%s%s/api/reservations?from=2021-06-04T12:00:00.588759200Z&to=2021-06-10T12:00:00.588759200Z",
            localhost, port));
    var rs = restTemplate.exchange(uri, HttpMethod.GET, null, ReservationResponse[].class);
    Assert.assertNotNull(rs.getBody());
    Assert.assertEquals(NumberUtils.INTEGER_TWO.intValue(), rs.getBody().length);
    Assert.assertTrue(Arrays.stream(rs.getBody()).anyMatch(rt -> rt.getHotelId().equals(NumberUtils.INTEGER_TWO.intValue())));
    Assert.assertTrue(Arrays.stream(rs.getBody()).anyMatch(rt -> rt.getRoomTypeId().equals(NumberUtils.INTEGER_TWO.intValue())));
  }
}
