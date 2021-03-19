package au.com.bueno.search.repository.file.csv;

import au.com.bueno.search.repository.file.csv.file.csv.CsvFileInputReader;
import au.com.bueno.search.repository.file.csv.file.csv.dto.Device;
import au.com.bueno.search.repository.file.csv.file.csv.dto.Sensor;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.core.env.Environment;

import java.time.ZonedDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsIterableContaining.hasItem;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CsvFileInputReaderTest {
  @Test
  void shouldReturnDeviceObjectFromDataCsvFile() {
    Environment env = mock(Environment.class);
    CsvFileInputReader csvFileInputReader = new CsvFileInputReader(env);
    when(env.getProperty("DATA_FILE")).thenReturn("src/test/resources/data.csv");

    List<Device> devices = csvFileInputReader.read();

    Sensor sensor = new Sensor();
    sensor.setName("OUTDOOR_HUMID");
    sensor.setType("NUMBER");
    sensor.setTags(List.of("his", "outside", "humidity", "sensor", "air"));
    sensor.setCreatedAt(ZonedDateTime.parse("2020-04-21T15:47:28+00"));
    sensor.setUpdatedAt(ZonedDateTime.parse("2020-07-06T17:07:07+00"));
    sensor.setUnit("%RH");
    sensor.setReadingAt(ZonedDateTime.parse("2021-02-02T23:55:00+00"));
    sensor.setReadingValue(34.52);
    Device device = new Device();
    device.setId("e07c57cc-cf7d-4cf2-959e-b0d506929aae");
    device.setName("_AHU1_MAIN");
    device.setSensor(sensor);

    assertThat(devices, hasItem(device));
  }

  @Test
  void shouldReturnAllDevicesFromDataCsvFile() {
    Environment env = mock(Environment.class);
    CsvFileInputReader csvFileInputReader = new CsvFileInputReader(env);
    when(env.getProperty("DATA_FILE")).thenReturn("src/test/resources/data.csv");

    List<Device> devices = csvFileInputReader.read();

    assertThat(devices.size(), Is.is(1728));
  }
}