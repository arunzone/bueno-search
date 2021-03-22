package au.com.bueno.search.index;

import au.com.bueno.search.entity.Device;
import au.com.bueno.search.index.query.Term;
import au.com.bueno.search.index.query.TermBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Arrays.stream;

@Component
public class InMemoryIndexBuilder {

  private final TermBuilder termBuilder;
  private Map<Term, Set<String>> index;
  private Collection<Device> devices;

  public InMemoryIndexBuilder(@Autowired TermBuilder termBuilder) {
    this.termBuilder = termBuilder;
  }

  public InMemoryIndexBuilder withDevices(Collection<Device> devices) {
    this.devices = devices;
    return this;
  }

  public InMemoryIndexBuilder withIndex(Map<Term, Set<String>> index) {
    this.index = index;
    return this;
  }

  public void build() {
    devices.forEach(this::index);
  }

  private void index(Device device) {
    indexDevice(device);
    indexSensors(device);
  }

  private void indexDevice(Device device) {
    Field[] fields = device.getClass().getDeclaredFields();
    stream(fields)
        .filter(field -> !field.getType().equals(List.class))
        .forEach(field -> index(device, field, device.getId()));
  }

  private void indexSensors(Device device) {
    device.getSensors().forEach(
        sensor -> {
          Field[] sensorFields = sensor.getClass().getDeclaredFields();
          stream(sensorFields).forEach(
              sensorField -> index(sensor, sensorField, device.getId())
          );
        }
    );
  }

  private <T> void index(T reference, Field field, String id) {
    field.setAccessible(true);
    try {
      indexField(reference, field, id);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  private <T> void indexField(T reference, Field field, String id) throws IllegalAccessException {
    Term term = termBuilder.build(reference, field);
    index.putIfAbsent(term, new HashSet<>());
    index.get(term).add(id);
  }
}
