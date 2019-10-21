package com.my.xunwu.service.house;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.xunwu.entity.SupportAddress;
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

}
