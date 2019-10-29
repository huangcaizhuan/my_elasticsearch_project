package com.my.xunwu.service.house;

import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.xunwu.base.LoginUserUtil;
import com.my.xunwu.entity.House;
import com.my.xunwu.repository.HouseRepository;
import com.my.xunwu.service.ServiceResult;
import com.my.xunwu.web.dto.HouseDTO;
import com.my.xunwu.web.form.HouseForm;

/**
 * 房屋接口实现类
 * @author hcz
 * @date 2019.10.29
 */
@Service
public class HouseServiceImpl implements IHouseService{

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private HouseRepository houseRepository;
	
	@Override
	public ServiceResult<HouseDTO> save(HouseForm houseForm) {

		House house = new House();
		modelMapper.map(houseForm, house);
		
		Date now = new Date();
		house.setCreateTime(now);
		house.setLastUpdateTime(now);
		house.setAdminId(LoginUserUtil.getLonginUserId());
		houseRepository.save(house);
		
		return null;
	}

}
