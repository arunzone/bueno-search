package au.com.bueno.search.repository.file.csv.file.csv.dto;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvRecurse;
import lombok.Data;

@Data
public class Device {
  @CsvBindByName(column = "device_id")
  private String id;
  @CsvBindByName(column = "device_name")
  private String name;
  @CsvRecurse
  private Sensor sensor;
}
