package com.incubator.dbs.hotelservice.intergration;

import com.incubator.dbs.hotelservice.service.connector.ReservationConnector;
import java.time.Instant;
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
    ids = "com.incubator.dbs.reservationservice:reservation-service:+:stubs:8096")
@EnableFeignClients(clients = ReservationConnector.class)
@Import({FeignAutoConfiguration.class, HttpMessageConvertersAutoConfiguration.class})
@TestPropertySource(properties = {
    "reservation.host=http://localhost:8096",
    "reservation.getReservation=/api/reservations"
})
public class ReservationServiceTest {

  @Autowired
  private ReservationConnector reservationConnector;

  @Test
  public void getAllReservation_shouldWork() {
    var from = Instant.parse("2021-06-04T12:00:00.588759200Z");
    var to = Instant.parse("2021-06-10T12:00:00.588759200Z");
    var rs = reservationConnector.getAllReservationInTime(from, to);
    Assert.assertNotNull(rs);
    Assert.assertEquals(NumberUtils.INTEGER_TWO.intValue(), rs.size());
    Assert.assertTrue(rs.stream().anyMatch(rt -> rt.getHotelId().equals(NumberUtils.INTEGER_TWO.intValue())));
    Assert.assertTrue(rs.stream().anyMatch(rt -> rt.getRoomTypeId().equals(NumberUtils.INTEGER_TWO.intValue())));
  }
}
