package au.com.bueno.search.controller;

import au.com.bueno.search.controller.dto.DeviceResponseDto;
import au.com.bueno.search.service.DeviceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeviceSearchController.class)
class DeviceSearchControllerTest {
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private DeviceService service;

  @Test
  public void shouldReturnDeviceFromService() throws Exception {
    DeviceResponseDto deviceResponseDto = new DeviceResponseDto();
    deviceResponseDto.setId("abc");
    deviceResponseDto.setName("name");
    when(service.findById("abc")).thenReturn(deviceResponseDto);
    this.mockMvc.perform(get("/devices/abc")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.name", is("name")))
        .andExpect(jsonPath("$.id", is("abc")));
  }

  @Test
  public void shouldReturnAllMatchingDevicesFromService() throws Exception {
    DeviceResponseDto deviceResponseDto = new DeviceResponseDto();
    deviceResponseDto.setId("abc");
    deviceResponseDto.setName("name");
    when(service.findAll(Map.of())).thenReturn(List.of(deviceResponseDto));
    this.mockMvc.perform(get("/devices")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0].name", is("name")))
        .andExpect(jsonPath("$[0].id", is("abc")));
  }
}