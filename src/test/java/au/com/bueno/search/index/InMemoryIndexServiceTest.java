package au.com.bueno.search.index;

import au.com.bueno.search.entity.Device;
import au.com.bueno.search.entity.Sensor;
import au.com.bueno.search.index.query.SimpleAnyMatchQuery;
import au.com.bueno.search.index.query.Term;
import au.com.bueno.search.index.query.TermBuilder;
import au.com.bueno.search.repository.Repository;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.mockito.Mockito.when;

class InMemoryIndexServiceTest {
  @Mock
  private Repository<Device, String> repository;
  private InMemoryIndexService service;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    service = new InMemoryIndexService(repository, new InMemoryIndexBuilder(new TermBuilder<Device>()));
    when(repository.findAll()).thenReturn(List.of(deviceEntity(), anotherDeviceEntity()));
  }

  @Test
  void shouldReturnAllDeviceIdForEmptySearchTerm() {
    SimpleAnyMatchQuery query = new SimpleAnyMatchQuery(10, 0, List.of());
    Set<String> ids = service.search(query);

    assertThat(ids, containsInAnyOrder("e07c57cc-cf7d-4cf2-959e-b0d506929aae", "e07c57cc-cf7d-4cf2-959e-b0d506929aaf"));
  }

  @Test
  void shouldReturnDeviceIdForMatchingDeviceName() {
    SimpleAnyMatchQuery query = new SimpleAnyMatchQuery(1, 0, List.of(new Term("device.name", "_AHU1_MAIN")));
    Set<String> ids = service.search(query);

    assertThat(ids, contains("e07c57cc-cf7d-4cf2-959e-b0d506929aae"));
  }

  @Test
  void shouldReturnDeviceIdForMatchingDeviceId() {
    List<Term> terms = List.of(new Term(
        "device.id",
        "e07c57cc-cf7d-4cf2-959e-b0d506929aae"));
    SimpleAnyMatchQuery query = new SimpleAnyMatchQuery(1, 0, terms);
    Set<String> ids = service.search(query);

    assertThat(ids, contains("e07c57cc-cf7d-4cf2-959e-b0d506929aae"));
  }

  @Test
  void shouldReturnDeviceIdForMatchingSensorType() {
    List<Term> terms = List.of(new Term(
        "sensor.type",
        "NUMBER"));
    SimpleAnyMatchQuery query = new SimpleAnyMatchQuery(1, 0, terms);
    Set<String> ids = service.search(query);

    assertThat(ids, contains("e07c57cc-cf7d-4cf2-959e-b0d506929aae"));
  }

  @Test
  void shouldReturnDeviceIdForMatchingSensorName() {
    List<Term> terms = List.of(new Term(
        "sensor.name",
        "OUTDOOR_HUMID"));
    SimpleAnyMatchQuery query = new SimpleAnyMatchQuery(1, 0, terms);
    Set<String> ids = service.search(query);

    assertThat(ids, contains("e07c57cc-cf7d-4cf2-959e-b0d506929aae"));
  }

  @Test
  void shouldReturnDeviceIdForMatchingSensorUnit() {
    List<Term> terms = List.of(new Term(
        "sensor.unit",
        "%RH"));
    SimpleAnyMatchQuery query = new SimpleAnyMatchQuery(1, 0, terms);
    Set<String> ids = service.search(query);

    assertThat(ids, contains("e07c57cc-cf7d-4cf2-959e-b0d506929aae"));
  }

  @Test
  void shouldReturnDeviceIdForMatchingSensorReadingValue() {
    List<Term> terms = List.of(new Term(
        "sensor.readingValue",
        "34.52"));
    SimpleAnyMatchQuery query = new SimpleAnyMatchQuery(1, 0, terms);
    Set<String> ids = service.search(query);

    assertThat(ids, contains("e07c57cc-cf7d-4cf2-959e-b0d506929aae"));
  }

  @Test
  void shouldReturnDeviceIdForMatchingSensorReadingAt() {
    List<Term> terms = List.of(new Term(
        "sensor.readingAt",
        "2021-02-02T23:55Z"));
    SimpleAnyMatchQuery query = new SimpleAnyMatchQuery(1, 0, terms);
    Set<String> ids = service.search(query);

    assertThat(ids, contains("e07c57cc-cf7d-4cf2-959e-b0d506929aae"));
  }

  @Test
  void shouldReturnDeviceIdForAllMatchingSensorReadingValueOrSensorUnit() {
    List<Term> terms = List.of(
        new Term(
            "sensor.readingAt",
            "2021-02-02T23:55Z"),
        new Term(
            "sensor.unit",
            "%RH")
    );
    SimpleAnyMatchQuery query = new SimpleAnyMatchQuery(2, 0, terms);
    Set<String> ids = service.search(query);

    assertThat(ids, contains("e07c57cc-cf7d-4cf2-959e-b0d506929aae"));
  }

  @Test
  void shouldReturnMultipleDeviceIdForAllMatchingSensorReadingValueOrSensorUnit() {
    List<Term> terms = List.of(
        new Term(
            "sensor.readingAt",
            "2021-02-02T23:55Z"),
        new Term(
            "sensor.unit",
            "%ZH")
    );
    SimpleAnyMatchQuery query = new SimpleAnyMatchQuery(2, 0, terms);
    Set<String> ids = service.search(query);

    assertThat(ids, containsInAnyOrder("e07c57cc-cf7d-4cf2-959e-b0d506929aae", "e07c57cc-cf7d-4cf2-959e-b0d506929aaf"));
  }

  @Test
  void shouldReturnSingleDeviceIdForAllMatchingTermByOffsetAndLimit() {
    List<Term> terms = List.of(
        new Term(
            "sensor.readingAt",
            "2021-02-02T23:55Z"),
        new Term(
            "sensor.unit",
            "%ZH")
    );
    SimpleAnyMatchQuery query = new SimpleAnyMatchQuery(2, 1, terms);
    Set<String> ids = service.search(query);

    assertThat(ids.size(), Is.is(1));
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

  private Device anotherDeviceEntity() {
    Sensor sensorEntity = new Sensor(
        "OUTDOOR_HUMIDITY",
        "STRING",
        List.of("his", "outside", "humidity", "sensor", "air"),
        ZonedDateTime.parse("2020-04-21T15:47:28+00"),
        ZonedDateTime.parse("2020-07-06T17:07:07+00"),
        "%ZH",
        ZonedDateTime.parse("2021-02-02T23:56:00+00"),
        34.53
    );
    Device deviceEntity = new Device(
        "e07c57cc-cf7d-4cf2-959e-b0d506929aaf",
        "_AHU2_MAIN",
        List.of(sensorEntity));
    return deviceEntity;
  }

}