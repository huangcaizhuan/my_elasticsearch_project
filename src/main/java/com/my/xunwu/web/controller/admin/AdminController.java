package com.my.xunwu.web.controller.admin;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.my.xunwu.base.ApiDataTableResponse;
import com.my.xunwu.base.ApiResponse;
import com.my.xunwu.entity.SupportAddress;
import com.my.xunwu.service.ServiceMultiResult;
import com.my.xunwu.service.ServiceResult;
import com.my.xunwu.service.house.IAddressService;
import com.my.xunwu.service.house.IHouseService;
import com.my.xunwu.service.house.IQiNiuService;
import com.my.xunwu.web.dto.HouseDTO;
import com.my.xunwu.web.dto.HouseDetailDTO;
import com.my.xunwu.web.dto.QiNiuPutRet;
import com.my.xunwu.web.dto.SubwayDTO;
import com.my.xunwu.web.dto.SubwayStationDTO;
import com.my.xunwu.web.dto.SupportAddressDTO;
import com.my.xunwu.web.form.DataTableSearch;
import com.my.xunwu.web.form.HouseForm;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;

/**
 * 
 * @author huangcaizhuan
 *2019.7.30
 */
@Controller
public class AdminController {
	@Autowired
	private IQiNiuService iQiNiuService;
	@Autowired
	private Gson gson;
	@Autowired
	private IAddressService  addressService;
	@Autowired
	private IHouseService houseService;

	@GetMapping("admin/center")
	public String adminCenterPage() {
		return "admin/center";
	}
	
	@GetMapping("admin/welcome")
	public String adminWelcomePage() {
		return "admin/welcome";
	}
	
	@GetMapping("admin/login")
	public String adminLoginPage() {
		return "admin/login";
	}
	@GetMapping("/admin/add/house")
	public String addHousePage() {
		return "admin/house-add";
	}
	
	@PostMapping(value="/admin/upload/photo",consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
	public ApiResponse uploadPhoto(@RequestParam("file")MultipartFile file) {
		if (file.isEmpty()) {
            return ApiResponse.ofStatus(ApiResponse.Status.NOT_VALID_PARAMS);
        }
		//********实现本地上传****start
		
		String fileName = file.getOriginalFilename();
        File target = new File("F:/test/xunwu/images/"+fileName);
        try {
			file.transferTo(target);
		} catch (IOException e) {
			e.printStackTrace();
			return ApiResponse.ofStatus(ApiResponse.Status.INTERNAL_SERVICE_ERROR);
		}
        QiNiuPutRet ret =new QiNiuPutRet();//模拟七牛云上传返回的数据
        ret.setBucket("bucket");
        ret.setHash("hash");
        ret.setHeight(1024);
        ret.setKey(fileName);
        ret.setWidth(683);
		return ApiResponse.ofSuccess(ret);
      //*****实现本地上传****end 
		
		//七牛云上传
		/*try {
			InputStream inputStream = file.getInputStream();
			Response response = iQiNiuService.uploadFile(inputStream);
			if(response.isOK()) {
				QiNiuPutRet ret =gson.fromJson(response.bodyString(), QiNiuPutRet.class);
				return ApiResponse.ofSuccess(ret);
			}else {
				return ApiResponse.ofMessage(response.statusCode, response.getInfo());
			}
		} catch (QiniuException  e) {
			Response response  =  e.response;
			try {
				return ApiResponse.ofMessage(response.statusCode, response.bodyString());
			} catch (QiniuException e1) {
				return  ApiResponse.ofStatus(ApiResponse.Status.INTERNAL_SERVICE_ERROR);
			} 
		}catch (IOException e) {
			return  ApiResponse.ofStatus(ApiResponse.Status.INTERNAL_SERVICE_ERROR);
		}*/
	}
	/**
	 * 新增房源接口
	 * @param houseForm 房屋提交表单
	 * @param bindingResult 绑定的结果集
	 * @return
	 */
	@PostMapping("/admin/add/house")
	@ResponseBody
	public ApiResponse addHouse(@Valid @ModelAttribute("form-house-add")HouseForm houseForm,BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return new ApiResponse(HttpStatus.BAD_REQUEST.value(),bindingResult.getAllErrors().get(0).getDefaultMessage(),null);
		}
		if(houseForm.getPhotos() == null || houseForm.getCover()==null) {
			return ApiResponse.ofMessage(HttpStatus.BAD_REQUEST.value(), "必须上传图片");
		}
		
		Map<SupportAddress.Level, SupportAddressDTO> addressMap = addressService.findCityAndRegion(houseForm.getCityEnName(), houseForm.getRegionEnName());
		if (addressMap.keySet().size() != 2) {
            return ApiResponse.ofStatus(ApiResponse.Status.NOT_VALID_PARAMS);
        }
		ServiceResult<HouseDTO> result= houseService.save(houseForm);
		if(result.getSuccess()) {
			return ApiResponse.ofSuccess(result.getResult());
		}
		
		return ApiResponse.ofSuccess(ApiResponse.Status.NOT_VALID_PARAMS);
	}
	
	@GetMapping("/admin/house/list")
	public String houseListPage() {
		return "admin/house-list";
	}
	
	@PostMapping("/admin/houses")
	@ResponseBody
	public ApiDataTableResponse houses(@ModelAttribute DataTableSearch searchBody) {
		
		ServiceMultiResult<HouseDTO> result = houseService.adminQuery(searchBody);
		
		ApiDataTableResponse response = new ApiDataTableResponse(ApiResponse.Status.SUCCESS);
		response.setData(result.getResult());
		response.setRecordsFiltered(result.getTotal());
		response.setRecordsTotal(result.getTotal());
		response.setDraw(searchBody.getDraw());
		return response;
	}
	
	@GetMapping("/admin/house/edit")
	public String houseEditPage(@RequestParam("id")Long id,Model model) {
		if(id ==null || id < 1) {
			return "404";
		}
		
		ServiceResult<HouseDTO>  serviceResult = houseService.findCompleteOne(id);
		HouseDTO result = serviceResult.getResult();
		model.addAttribute("house",result);
		
		Map<SupportAddress.Level, SupportAddressDTO> adressMap = addressService.findCityAndRegion(result.getCityEnName(), result.getRegionEnName());
		model.addAttribute("city",adressMap.get(SupportAddress.Level.CITY));
		model.addAttribute("region",adressMap.get(SupportAddress.Level.REGION));
		
		HouseDetailDTO detailDTO = result.getHouseDetail();
        ServiceResult<SubwayDTO> subwayServiceResult = addressService.findSubway(detailDTO.getSubwayLineId());
        if (subwayServiceResult.isSuccess()) {
            model.addAttribute("subway", subwayServiceResult.getResult());
        }

        ServiceResult<SubwayStationDTO> subwayStationServiceResult = addressService.findSubwayStation(detailDTO.getSubwayStationId());
        if (subwayStationServiceResult.isSuccess()) {
            model.addAttribute("station", subwayStationServiceResult.getResult());
        }
		
		return "admin/house-edit";
	}
	
	/**
	 * 编辑接口
	 * @param houseForm
	 * @param bindingResult
	 * @return
	 */
	@PostMapping("/admin/house/edit")
	@ResponseBody
	public ApiResponse saveHouse(@Valid @ModelAttribute("form-house-edit")HouseForm houseForm,BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return new ApiResponse(HttpStatus.BAD_REQUEST.value(),bindingResult.getAllErrors().get(0).getDefaultMessage(),null);
		}
		Map<SupportAddress.Level,SupportAddressDTO> addressMap = addressService.findCityAndRegion(houseForm.getCityEnName(), houseForm.getRegionEnName());
		if(addressMap.keySet().size() != 2) {
			return ApiResponse.ofSuccess(ApiResponse.Status.NOT_VALID_PARAMS);
		}
		ServiceResult result = houseService.update(houseForm);
		 if (result.isSuccess()) {
            return ApiResponse.ofSuccess(null);
        }

        ApiResponse response = ApiResponse.ofStatus(ApiResponse.Status.BAD_REQUEST);
        response.setMessage(result.getMessage());
        return response;
	}
}
