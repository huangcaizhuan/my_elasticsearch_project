package com.my.xunwu.web.controller.house;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.xunwu.base.ApiResponse;
import com.my.xunwu.service.ServiceMultiResult;
import com.my.xunwu.service.house.IAddressService;
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
}
