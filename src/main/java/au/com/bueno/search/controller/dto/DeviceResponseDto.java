package au.com.bueno.search.controller.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;

import java.util.List;

@Generated
@EqualsAndHashCode
@Data
public class DeviceResponseDto {
  @Getter
  private String id;
  @Getter
  private String name;
  @Getter
  private List<SensorResponseDto> sensors;
}
