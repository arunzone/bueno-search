package au.com.bueno.search.service;

import au.com.bueno.search.controller.dto.DeviceResponseDto;
import au.com.bueno.search.entity.Device;
import au.com.bueno.search.repository.DeviceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeviceService {
  private final ModelMapper modelMapper;
  private final DeviceRepository deviceRepository;

  public DeviceService(@Autowired ModelMapper modelMapper,
                       @Autowired DeviceRepository deviceRepository) {
    this.modelMapper = modelMapper;
    this.deviceRepository = deviceRepository;
  }

  public DeviceResponseDto findById(String id) {
    Device device = deviceRepository.findById(id);
    return modelMapper.map(device, DeviceResponseDto.class);
  }
}
