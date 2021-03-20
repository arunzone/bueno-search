package au.com.bueno.search.controller.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;

@Generated
@EqualsAndHashCode
@Data
public class SensorResponseDto {
  @Getter
  private String name;
  @Getter
  private String type;
  @Getter
  private List<String> tags;
  @Getter
  private ZonedDateTime createdAt;
  @Getter
  private ZonedDateTime updatedAt;
  @Getter
  private String unit;
  @Getter
  private ZonedDateTime readingAt;
  @Getter
  private Double readingValue;
}
