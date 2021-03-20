package au.com.bueno.search.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;

import java.util.List;

@Generated
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
