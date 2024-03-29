package com.my.xunwu.service.house;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.xunwu.entity.Subway;
import com.my.xunwu.entity.SubwayStation;
import com.my.xunwu.entity.SupportAddress;
import com.my.xunwu.entity.SupportAddress.Level;
import com.my.xunwu.repository.SubwayRepository;
import com.my.xunwu.repository.SubwayStationRepository;
import com.my.xunwu.repository.SupportAddressRepository;
import com.my.xunwu.service.ServiceMultiResult;
import com.my.xunwu.service.ServiceResult;
import com.my.xunwu.web.dto.SubwayDTO;
import com.my.xunwu.web.dto.SubwayStationDTO;
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
	private SubwayRepository subwayRepository;
	@Autowired
	private SubwayStationRepository subwayStationRepository;
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

	@Override
	public List<SubwayDTO> findAllSubwayByCity(String cityEnName) {
		List<SubwayDTO> result = new ArrayList<>();
        List<Subway> subways = subwayRepository.findAllByCityEnName(cityEnName);
        if (subways.isEmpty()) {
            return result;
        }

        subways.forEach(subway -> result.add(modelMapper.map(subway, SubwayDTO.class)));
        return result;
	}

	@Override
	public List<SubwayStationDTO> findAllStationBySubway(Long subwayId) {
		List<SubwayStationDTO> result = new ArrayList<>();
		List<SubwayStation> stations = subwayStationRepository.findAllBySubwayId(subwayId);
		if(stations.isEmpty()) {
			return result;
		}
		stations.forEach(station -> result.add(modelMapper.map(station, SubwayStationDTO.class)));
		return result;
	}

	@Override
	public ServiceResult<SubwayStationDTO> findSubwayStation(Long subwayStationId) {
	 if (subwayStationId == null) {
            return ServiceResult.notFound();
        }
        SubwayStation station = subwayStationRepository.findOne(subwayStationId);
        if (station == null) {
            return ServiceResult.notFound();
        }
        return ServiceResult.of(modelMapper.map(station, SubwayStationDTO.class));
	}

	@Override
	public ServiceResult<SubwayDTO> findSubway(Long subwayLineId) {
		if (subwayLineId == null) {
            return ServiceResult.notFound();
        }
        Subway subway = subwayRepository.findOne(subwayLineId);
        if (subway == null) {            return ServiceResult.notFound();
        }
        return ServiceResult.of(modelMapper.map(subway, SubwayDTO.class));
	}

}
