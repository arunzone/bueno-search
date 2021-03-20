package au.com.bueno.search.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@EqualsAndHashCode
@AllArgsConstructor
public class Device {
  @Getter
  private final String id;
  @Getter
  private final String name;
  @Getter
  private final List<Sensor> sensors;
}
