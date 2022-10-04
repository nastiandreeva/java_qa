package ru.stqa.auto.soap;

import com.lavasoft.GeoIPService;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class GeoIpServiceTests {

  @Test
  public void testMyIp() {
    String geoIp = new GeoIPService().getGeoIPServiceSoap12().getIpLocation("37.79.33.125");
    assertEquals(geoIp, "RUS");
    System.out.println(geoIp);
  }

  @Test
  public void testInvalidIp() {
    String geoIp = new GeoIPService().getGeoIPServiceSoap12().getIpLocation("37.79.33.xxx");
    System.out.println(geoIp);
  }
}
