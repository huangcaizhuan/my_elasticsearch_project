package com.my.xunwu.service.house;

import com.my.xunwu.service.ServiceMultiResult;
import com.my.xunwu.web.dto.SupportAddressDTO;

/**
 * 地址服务接口
 * @author hcz
 * @date 2019.10.21
 */
public interface IAddressService {
	ServiceMultiResult<SupportAddressDTO> findAllCities();
}
