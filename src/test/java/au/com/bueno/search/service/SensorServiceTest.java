package au.com.bueno.search.service;

import au.com.bueno.search.controller.dto.DeviceResponseDto;
import au.com.bueno.search.controller.dto.SensorResponseDto;
import au.com.bueno.search.entity.Device;
import au.com.bueno.search.entity.Sensor;
import au.com.bueno.search.index.InMemoryIndexService;
import au.com.bueno.search.index.query.SimpleAnyMatchQuery;
import au.com.bueno.search.index.query.SimpleAnyMatchQueryBuilder;
import au.com.bueno.search.repository.DeviceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SensorServiceTest {
  @Mock
  private DeviceRepository deviceRepository;
  @Mock
  private InMemoryIndexService inMemoryIndexService;
  @Mock
  private SimpleAnyMatchQueryBuilder simpleAnyMatchQueryBuilder;
  private SensorService sensorService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    sensorService = new SensorService(new ModelMapper(), deviceRepository, inMemoryIndexService, simpleAnyMatchQueryBuilder);
  }

  @Test
  void shouldReturnMatchingDevices() {
    Map<String, String> parameters = mock(Map.class);
    SimpleAnyMatchQuery query = mock(SimpleAnyMatchQuery.class);

    when(simpleAnyMatchQueryBuilder.queryFrom(parameters)).thenReturn(query);
    Set<String> ids = Set.of("e07c57cc-cf7d-4cf2-959e-b0d506929aae");
    when(inMemoryIndexService.search(query)).thenReturn(ids);
    when(deviceRepository.findByIds(ids)).thenReturn(List.of(deviceEntity()));
    List<SensorResponseDto> sensorResponseDtos = sensorService.findAll(parameters);

    SensorResponseDto sensorResponseDto = deviceResponseDto().getSensors().get(0);
    assertThat(sensorResponseDtos, contains(sensorResponseDto));
  }

  private DeviceResponseDto deviceResponseDto() {
    SensorResponseDto sensorResponse = new SensorResponseDto();
    sensorResponse.setName("OUTDOOR_HUMID");
    sensorResponse.setType("NUMBER");
    sensorResponse.setTags(List.of("his", "outside", "humidity", "sensor", "air"));
    sensorResponse.setCreatedAt(ZonedDateTime.parse("2020-04-21T15:47:28+00"));
    sensorResponse.setUpdatedAt(ZonedDateTime.parse("2020-07-06T17:07:07+00"));
    sensorResponse.setUnit("%RH");
    sensorResponse.setReadingAt(ZonedDateTime.parse("2021-02-02T23:55:00+00"));
    sensorResponse.setReadingValue(34.52);
    DeviceResponseDto deviceResponse = new DeviceResponseDto();
    deviceResponse.setId("e07c57cc-cf7d-4cf2-959e-b0d506929aae");
    deviceResponse.setName("_AHU1_MAIN");
    deviceResponse.setSensors(List.of(sensorResponse));
    return deviceResponse;
  }

  private Device deviceEntity() {
    Sensor sensorEntity = new Sensor(
        "OUTDOOR_HUMID",
        "NUMBER",
        List.of("his", "outside", "humidity", "sensor", "air"),
        ZonedDateTime.parse("2020-04-21T15:47:28+00"),
        ZonedDateTime.parse("2020-07-06T17:07:07+00"),
        "%RH",
        ZonedDateTime.parse("2021-02-02T23:55:00+00"),
        34.52
    );
    Device deviceEntity = new Device(
        "e07c57cc-cf7d-4cf2-959e-b0d506929aae",
        "_AHU1_MAIN",
        List.of(sensorEntity));
    return deviceEntity;
  }
}