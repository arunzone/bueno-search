package au.com.bueno.search.service;

import au.com.bueno.search.controller.dto.DeviceResponseDto;
import au.com.bueno.search.entity.Device;
import au.com.bueno.search.repository.DeviceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
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
    return device == null ? null : modelMapper.map(device, DeviceResponseDto.class);
  }

  public List<DeviceResponseDto> findAll(Map<String, String> allParams) {
    Integer max = 10;
    if (allParams.containsKey("pageSize")) {
      max = Integer.valueOf(allParams.get("pageSize"));
    }
    return deviceRepository.findAll().stream()
        .limit(max)
        .map(device -> modelMapper.map(device, DeviceResponseDto.class))
        .collect(Collectors.toList());
  }
}
