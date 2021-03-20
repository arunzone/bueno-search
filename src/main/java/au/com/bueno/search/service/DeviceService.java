package au.com.bueno.search.service;

import au.com.bueno.search.controller.dto.DeviceResponseDto;
import au.com.bueno.search.entity.Device;
import au.com.bueno.search.index.InMemoryIndexService;
import au.com.bueno.search.index.query.SimpleAnyMatchQuery;
import au.com.bueno.search.index.query.SimpleAnyMatchQueryBuilder;
import au.com.bueno.search.repository.DeviceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DeviceService {
  private final ModelMapper modelMapper;
  private final DeviceRepository deviceRepository;
  private final InMemoryIndexService inMemoryIndexService;
  private final SimpleAnyMatchQueryBuilder simpleAnyMatchQueryBuilder;

  public DeviceService(@Autowired ModelMapper modelMapper,
                       @Autowired DeviceRepository deviceRepository,
                       @Autowired InMemoryIndexService inMemoryIndexService,
                       @Autowired SimpleAnyMatchQueryBuilder simpleAnyMatchQueryBuilder) {
    this.modelMapper = modelMapper;
    this.deviceRepository = deviceRepository;
    this.inMemoryIndexService = inMemoryIndexService;
    this.simpleAnyMatchQueryBuilder = simpleAnyMatchQueryBuilder;
  }

  public DeviceResponseDto findById(String id) {
    Device device = deviceRepository.findById(id);
    return device == null ? null : modelMapper.map(device, DeviceResponseDto.class);
  }

  public List<DeviceResponseDto> findAll(Map<String, String> allParams) {
    SimpleAnyMatchQuery query = simpleAnyMatchQueryBuilder.queryFrom(allParams);
    Set<String> ids = inMemoryIndexService.search(query);
    return deviceRepository.findByIds(ids).stream()
        .map(device -> modelMapper.map(device, DeviceResponseDto.class))
        .collect(Collectors.toList());
  }
}
