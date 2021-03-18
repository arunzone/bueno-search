package au.com.bueno.search.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeviceSearchController {
  @GetMapping("/")
  public String index() {
    return "First req";
  }
}
