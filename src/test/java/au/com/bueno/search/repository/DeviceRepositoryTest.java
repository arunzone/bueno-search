package au.com.bueno.search.repository;

import au.com.bueno.search.entity.Device;
import au.com.bueno.search.entity.Sensor;
import au.com.bueno.search.repository.file.csv.file.csv.CsvFileInputReader;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
  void shouldReturnADeviceByIdWhenLoaded() {
    CsvFileInputReader csvFileInputReader = mock(CsvFileInputReader.class);
    DeviceRepository deviceRepository = new DeviceRepository(csvFileInputReader);

    List<au.com.bueno.search.repository.file.csv.file.csv.dto.Device> devices = List.of(deviceDto());
    when(csvFileInputReader.read()).thenReturn(devices);

    Device expectedDevice = deviceRepository.findById("e07c57cc-cf7d-4cf2-959e-b0d506929aae");

    assertThat(expectedDevice, is(deviceEntity()));
  }

  @Test
  void shouldNotLoadWhenAlreadyLoaded() {
    CsvFileInputReader csvFileInputReader = mock(CsvFileInputReader.class);
    DeviceRepository deviceRepository = new DeviceRepository(csvFileInputReader);

    List<au.com.bueno.search.repository.file.csv.file.csv.dto.Device> devices = List.of(deviceDto());
    when(csvFileInputReader.read()).thenReturn(devices);

    deviceRepository.findById("abc");
    deviceRepository.findById("abc");

    verify(csvFileInputReader, times(1)).read();
  }

  @Test
  void shouldReturnAllDevicesWhenLoaded() {
    CsvFileInputReader csvFileInputReader = mock(CsvFileInputReader.class);
    DeviceRepository deviceRepository = new DeviceRepository(csvFileInputReader);

    List<au.com.bueno.search.repository.file.csv.file.csv.dto.Device> devices = List.of(deviceDto());
    when(csvFileInputReader.read()).thenReturn(devices);

    Collection<Device> device = deviceRepository.findAll();

    assertThat(device, contains(deviceEntity()));
  }

  @Test
  void shouldReturnMatchingDevices() {
    CsvFileInputReader csvFileInputReader = mock(CsvFileInputReader.class);
    DeviceRepository deviceRepository = new DeviceRepository(csvFileInputReader);

    List<au.com.bueno.search.repository.file.csv.file.csv.dto.Device> devices = List.of(deviceDto());
    when(csvFileInputReader.read()).thenReturn(devices);

    Collection<Device> device = deviceRepository.findByIds(Set.of("e07c57cc-cf7d-4cf2-959e-b0d506929aae"));

    assertThat(device, contains(deviceEntity()));
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

  private au.com.bueno.search.repository.file.csv.file.csv.dto.Device deviceDto() {
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
    return device;
  }
}