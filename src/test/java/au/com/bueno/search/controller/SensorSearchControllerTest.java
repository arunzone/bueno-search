package au.com.bueno.search.controller;

import au.com.bueno.search.controller.dto.SensorResponseDto;
import au.com.bueno.search.service.SensorService;
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

@WebMvcTest(SensorSearchController.class)
class SensorSearchControllerTest {
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private SensorService service;

  @Test
  public void shouldReturnMatchingSensorsFromService() throws Exception {
    SensorResponseDto sensorResponseDto = new SensorResponseDto();
    sensorResponseDto.setType("abc");
    sensorResponseDto.setName("name");
    when(service.findAll(Map.of())).thenReturn(List.of(sensorResponseDto));
    this.mockMvc.perform(get("/sensors")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0].name", is("name")))
        .andExpect(jsonPath("$[0].type", is("abc")));
  }
}