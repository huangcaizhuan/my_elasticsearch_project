package com.my.xunwu.service.house;

import java.util.Map;

import com.my.xunwu.entity.SupportAddress;
import com.my.xunwu.service.ServiceMultiResult;
import com.my.xunwu.web.dto.SupportAddressDTO;

/**
 * 地址服务接口
 * @author hcz
 * @date 2019.10.21
 */
public interface IAddressService {
	/**
     * 获取所有支持的城市列表
     * @return
     */
	ServiceMultiResult<SupportAddressDTO> findAllCities();
	 /**
     * 根据英文简写获取具体区域的信息
     * @param cityEnName
     * @param regionEnName
     * @return
     */
	Map<SupportAddress.Level, SupportAddressDTO> findCityAndRegion(String cityEnName,String regionEnName);
	/**
     * 根据城市英文简写获取该城市所有支持的区域信息
     * @param cityName
     * @return
     */
    ServiceMultiResult<SupportAddressDTO> findAllRegionsByCityName(String cityName);
}
