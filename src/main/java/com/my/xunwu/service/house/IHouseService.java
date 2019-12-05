package com.my.xunwu.service.house;

import com.my.xunwu.service.ServiceMultiResult;
import com.my.xunwu.service.ServiceResult;
import com.my.xunwu.web.dto.HouseDTO;
import com.my.xunwu.web.form.DataTableSearch;
import com.my.xunwu.web.form.HouseForm;

/**
 * 房屋管理服务接口
 * @author hcz
 * @date 2019.10.29
 */
public interface IHouseService {
	ServiceResult<HouseDTO> save(HouseForm houseForm);
	
	ServiceResult update(HouseForm houseForm);
	
	ServiceMultiResult<HouseDTO> adminQuery(DataTableSearch searchBody);
	/**
	 * 查询完整房源信息
	 * @param id
	 * @return
	 */
	ServiceResult<HouseDTO> findCompleteOne(Long id);
}
