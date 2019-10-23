package com.my.xunwu.web.controller.house;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.xunwu.base.ApiResponse;
import com.my.xunwu.service.ServiceMultiResult;
import com.my.xunwu.service.house.IAddressService;
import com.my.xunwu.web.dto.SubwayDTO;
import com.my.xunwu.web.dto.SubwayStationDTO;
import com.my.xunwu.web.dto.SupportAddressDTO;

@Controller
public class HouseController {
	@Autowired
	private IAddressService addressService;

	  /**
     * 获取支持城市列表
     * @return
     */
    @GetMapping("address/support/cities")
    @ResponseBody
    public ApiResponse getSupportCities() {
        ServiceMultiResult<SupportAddressDTO> result = addressService.findAllCities();
        if (result.getResultSize() == 0) {
            return ApiResponse.ofStatus(ApiResponse.Status.NOT_FOUND);
        }
        return ApiResponse.ofSuccess(result.getResult());
    }
    /**
     * 获取对应城市支持区域列表
     * @param cityEnName
     * @return
     */
    @GetMapping("address/support/regions")
    @ResponseBody
    public ApiResponse getSupportRegions(@RequestParam(name="city_name")String cityEnName) {
    	ServiceMultiResult<SupportAddressDTO> addressResult = addressService.findAllRegionsByCityName(cityEnName);
    	if(addressResult.getResult() == null || addressResult.getTotal() < 1) {
    		return ApiResponse.ofStatus(ApiResponse.Status.NOT_FOUND);
    	}
		return ApiResponse.ofSuccess(addressResult.getResult());
    }
    
    /**
     * 获取具体城市所支持的地铁线路
     * @param cityEnName
     * @return
     */
    @GetMapping("address/support/subway/line")
    @ResponseBody
    public ApiResponse getSupportSubwayLine(@RequestParam(name = "city_name") String cityEnName) {
        List<SubwayDTO> subways = addressService.findAllSubwayByCity(cityEnName);
        if (subways.isEmpty()) {
            return ApiResponse.ofStatus(ApiResponse.Status.NOT_FOUND);
        }

        return ApiResponse.ofSuccess(subways);
    }
    
    @GetMapping("address/support/subway/station")
    @ResponseBody
    public ApiResponse getSupportSubwayStation(@RequestParam(name="subway_id")Long subwayId) {
    	List<SubwayStationDTO> stationDTOs = addressService.findAllStationBySubway(subwayId);
    	if(stationDTOs.isEmpty()) {
    		return ApiResponse.ofStatus(ApiResponse.Status.NOT_FOUND);
    	}
    	return ApiResponse.ofSuccess(stationDTOs);
    }
}
