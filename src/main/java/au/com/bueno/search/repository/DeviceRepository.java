package au.com.bueno.search.repository;

import au.com.bueno.search.entity.Device;
import au.com.bueno.search.entity.Sensor;
import au.com.bueno.search.repository.file.csv.file.csv.CsvFileInputReader;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Repository
public class DeviceRepository implements Repository<Device, String> {

  private final CsvFileInputReader csvFileInputReader;
  private final Map<String, Device> devices = new HashMap<>();

  public DeviceRepository(@Autowired CsvFileInputReader csvFileInputReader) {
    this.csvFileInputReader = csvFileInputReader;
  }

  @Override
  public Device findById(String id) {
    loadDataWhenRequired();
    return devices.get(id);
  }

  private void loadDataWhenRequired() {
    if (devices.isEmpty()) {
      loadDevices();
    }
  }

  private void loadDevices() {
    csvFileInputReader.read().forEach(device -> {
          devices.putIfAbsent(device.getId(), new Device(device.getId(), device.getName(), new ArrayList<>()));
          Device currentDevice = devices.get(device.getId());
          List<Sensor> sensors = currentDevice.getSensors();
          sensors.add(sensorFrom(device));
        }
    );
  }

  private Sensor sensorFrom(au.com.bueno.search.repository.file.csv.file.csv.dto.Device device) {
    au.com.bueno.search.repository.file.csv.file.csv.dto.Sensor sensor = device.getSensor();
    return new Sensor(
        sensor.getName(),
        sensor.getType(),
        sensor.getTags(),
        sensor.getCreatedAt(),
        sensor.getUpdatedAt(),
        sensor.getUnit(),
        sensor.getReadingAt(),
        sensor.getReadingValue()
    );
  }

  public Collection<Device> findAll() {
    loadDataWhenRequired();
    return devices.values();
  }
}
