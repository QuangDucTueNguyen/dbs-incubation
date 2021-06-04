package com.incubator.dbs.reservationservice.intergration;

import com.incubator.dbs.reservationservice.model.dto.RoomTypeResponseDTO;
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
@AutoConfigureStubRunner(stubsMode = StubRunnerProperties.StubsMode.LOCAL, ids = "com.incubator.dbs:hotel-service")
public class HotelServiceTest {

  private RestTemplate restTemplate;
  private static final String localhost = "http://localhost:";
  @StubRunnerPort("hotel-service")
  int port;

  @Before
  public void setup() {
    restTemplate = new RestTemplate();
  }

  @Test
  public void getAllRoomType_shouldWork() {
    var uri = URI.create(String.format("%s%s/api/rooms/types", localhost, port));
    var rs = restTemplate.exchange(uri, HttpMethod.GET, null, RoomTypeResponseDTO[].class);
    Assert.assertNotNull(rs.getBody());
    Assert.assertEquals(NumberUtils.INTEGER_TWO.intValue(), rs.getBody().length);
    Assert.assertTrue(Arrays.stream(rs.getBody()).anyMatch(rt -> rt.getType().equals("MASTER")));
    Assert.assertTrue(Arrays.stream(rs.getBody()).anyMatch(rt -> rt.getType().equals("NORMAL")));
  }
}
