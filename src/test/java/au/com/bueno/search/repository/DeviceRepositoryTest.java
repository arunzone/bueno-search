package au.com.bueno.search.repository;

import au.com.bueno.search.entity.Device;
import au.com.bueno.search.entity.Sensor;
import au.com.bueno.search.repository.file.csv.file.csv.CsvFileInputReader;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.List;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DeviceRepositoryTest {

  @Test
  void shouldNotReturnNullWhenNotLoaded() {
    CsvFileInputReader csvFileInputReader = mock(CsvFileInputReader.class);
    DeviceRepository deviceRepository = new DeviceRepository(csvFileInputReader);
    Device device = deviceRepository.findById("abc");

    assertThat(device, is(nullValue()));
  }

  @Test
  void shouldReturnDeviceWhenLoaded() {
    au.com.bueno.search.repository.file.csv.file.csv.dto.Sensor sensor = new au.com.bueno.search.repository.file.csv.file.csv.dto.Sensor();
    sensor.setName("OUTDOOR_HUMID");
    sensor.setType("NUMBER");
    sensor.setTags(List.of("his", "outside", "humidity", "sensor", "air"));
    sensor.setCreatedAt(ZonedDateTime.parse("2020-04-21T15:47:28+00"));
    sensor.setUpdatedAt(ZonedDateTime.parse("2020-07-06T17:07:07+00"));
    sensor.setUnit("%RH");
    sensor.setReadingAt(ZonedDateTime.parse("2021-02-02T23:55:00+00"));
    sensor.setReadingValue(34.52);
    au.com.bueno.search.repository.file.csv.file.csv.dto.Device device = new au.com.bueno.search.repository.file.csv.file.csv.dto.Device();
    device.setId("e07c57cc-cf7d-4cf2-959e-b0d506929aae");
    device.setName("_AHU1_MAIN");
    device.setSensor(sensor);

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


    CsvFileInputReader csvFileInputReader = mock(CsvFileInputReader.class);
    DeviceRepository deviceRepository = new DeviceRepository(csvFileInputReader);

    List<au.com.bueno.search.repository.file.csv.file.csv.dto.Device> devices = List.of(device);
    when(csvFileInputReader.read()).thenReturn(devices);

    Device expectedDevice = deviceRepository.findById("e07c57cc-cf7d-4cf2-959e-b0d506929aae");

    assertThat(expectedDevice, is(deviceEntity));
  }
}