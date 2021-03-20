package au.com.bueno.search.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;

@Generated
@EqualsAndHashCode
@AllArgsConstructor
public class Sensor {
  @Getter
  private final String name;
  @Getter
  private final String type;
  @Getter
  private final List<String> tags;
  @Getter
  private final ZonedDateTime createdAt;
  @Getter
  private final ZonedDateTime updatedAt;
  @Getter
  private final String unit;
  @Getter
  private final ZonedDateTime readingAt;
  @Getter
  private final Double readingValue;
}
