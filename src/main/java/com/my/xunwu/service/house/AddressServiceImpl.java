package com.my.xunwu.service.house;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.xunwu.entity.SupportAddress;
import com.my.xunwu.entity.SupportAddress.Level;
import com.my.xunwu.repository.SupportAddressRepository;
import com.my.xunwu.service.ServiceMultiResult;
import com.my.xunwu.web.dto.SupportAddressDTO;

/**
 * 地址服务实现接口
 * @author hcz
 * @date 2019.10.21
 */
@Service
public class AddressServiceImpl implements IAddressService{
	
	@Autowired
	private SupportAddressRepository supportAddressRepository;
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public ServiceMultiResult<SupportAddressDTO> findAllCities() {
		List<SupportAddress> addresses= supportAddressRepository.findAllByLevel(SupportAddress.Level.CITY.getValue());
		List<SupportAddressDTO> addressDTOs = new ArrayList<>();
		for(SupportAddress supportAddress:addresses) {
			SupportAddressDTO target = modelMapper.map(supportAddress, SupportAddressDTO.class);
			addressDTOs.add(target);
		}
		return new ServiceMultiResult<>(addressDTOs.size(),addressDTOs);
	}

	@Override
	public Map<Level, SupportAddressDTO> findCityAndRegion(String cityEnName, String regionEnName) {
		Map<Level, SupportAddressDTO> result = new HashMap<>();
		
		SupportAddress city = supportAddressRepository.findByEnNameAndLevel(cityEnName, SupportAddress.Level.CITY.getValue());
		
		SupportAddress region = supportAddressRepository.findByEnNameAndBelongTo(regionEnName, city.getEnName());
		
		result.put(SupportAddress.Level.CITY, modelMapper.map(city, SupportAddressDTO.class));
		result.put(SupportAddress.Level.REGION, modelMapper.map(region, SupportAddressDTO.class));
		return result;
	}

	@Override
	public ServiceMultiResult<SupportAddressDTO> findAllRegionsByCityName(String cityName) {
		if(cityName == null) {
			return new ServiceMultiResult<>(0, null);
		}
		
		List<SupportAddressDTO> result = new ArrayList<>();
		
		List<SupportAddress> regions = supportAddressRepository.findAllByLevelAndBelongTo(SupportAddress.Level.REGION.getValue(), cityName);
		for(SupportAddress region:regions) {
			result.add(modelMapper.map(region, SupportAddressDTO.class));
		}
		return new ServiceMultiResult<>(regions.size(),result);
	}

}
