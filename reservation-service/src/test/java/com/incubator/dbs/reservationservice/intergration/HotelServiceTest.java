package com.incubator.dbs.reservationservice.intergration;

import com.incubator.dbs.reservationservice.service.connector.GuestServiceConnector;
import com.incubator.dbs.reservationservice.service.connector.HotelServiceConnector;
import org.apache.commons.lang3.math.NumberUtils;
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
    ids = "com.incubator.dbs:hotel-service:+:stubs:8097")
@EnableFeignClients(clients = HotelServiceConnector.class)
@Import({FeignAutoConfiguration.class, HttpMessageConvertersAutoConfiguration.class})
@TestPropertySource(properties = {
    "hotelService.host=http://localhost:8097",
    "hotelService.getAllRoomType=/api/rooms/types"
})
public class HotelServiceTest {

  @Autowired
  private HotelServiceConnector hotelServiceConnector;

  @Test
  public void getAllRoomType_shouldWork() {
    var rs = hotelServiceConnector.getAllRoomType();
    Assert.assertNotNull(rs);
    Assert.assertEquals(NumberUtils.INTEGER_TWO.intValue(), rs.size());
    Assert.assertTrue(rs.stream().anyMatch(rt -> rt.getType().equals("MASTER")));
    Assert.assertTrue(rs.stream().anyMatch(rt -> rt.getType().equals("NORMAL")));
  }
}
