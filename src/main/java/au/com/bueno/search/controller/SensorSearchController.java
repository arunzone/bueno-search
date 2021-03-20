package au.com.bueno.search.controller;

import au.com.bueno.search.controller.dto.SensorResponseDto;
import au.com.bueno.search.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class SensorSearchController {

  private final SensorService sensorService;

  public SensorSearchController(@Autowired SensorService sensorService) {
    this.sensorService = sensorService;
  }

  @GetMapping("/sensors")
  public List<SensorResponseDto> allSensorsDevices(@RequestParam Map<String, String> allParams) {
    return sensorService.findAll(allParams);
  }
}
