package au.com.bueno.search.repository.file.csv.file.csv.dto;

import au.com.bueno.search.repository.file.csv.file.csv.DateTimeConverter;
import au.com.bueno.search.repository.file.csv.file.csv.TagsConverter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
public class Sensor {
  @CsvBindByName(column = "sensor_name")
  private String name;
  @CsvBindByName(column = "sensor_type")
  private String type;
  @CsvCustomBindByName(column = "sensor_tags", converter = TagsConverter.class)
  private List<String> tags;
  @CsvCustomBindByName(column = "sensor_created_dt", converter = DateTimeConverter.class)
  private ZonedDateTime createdAt;
  @CsvCustomBindByName(column = "sensor_updated_dt", converter = DateTimeConverter.class)
  private ZonedDateTime updatedAt;
  @CsvBindByName(column = "sensor_unit")
  private String unit;
  @CsvCustomBindByName(column = "sensor_reading_dt", converter = DateTimeConverter.class)
  private ZonedDateTime readingAt;
  @CsvBindByName(column = "sensor_reading_value")
  private Double readingValue;
}
