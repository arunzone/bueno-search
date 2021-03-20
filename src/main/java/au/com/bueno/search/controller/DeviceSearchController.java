package au.com.bueno.search.controller;

import au.com.bueno.search.controller.dto.DeviceResponseDto;
import au.com.bueno.search.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class DeviceSearchController {

  private final DeviceService deviceService;

  public DeviceSearchController(@Autowired DeviceService deviceService) {
    this.deviceService = deviceService;
  }

  @GetMapping("/devices")
  public List<DeviceResponseDto> allDevices(@RequestParam Map<String, String> allParams) {
    return deviceService.findAll(allParams);
  }

  @GetMapping("/devices/{id}")
  public DeviceResponseDto device(@PathVariable String id) {
    return deviceService.findById(id);
  }
}
