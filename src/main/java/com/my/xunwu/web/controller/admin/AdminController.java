package com.my.xunwu.web.controller.admin;


import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.my.xunwu.base.ApiResponse;
import com.my.xunwu.service.house.IQiNiuService;
import com.my.xunwu.web.dto.QiNiuPutRet;
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
		/*
		String fileName = file.getOriginalFilename();
        File target = new File("F:/test/xunwu/images/"+fileName);
        try {
			file.transferTo(target);
		} catch (IOException e) {
			e.printStackTrace();
			return ApiResponse.ofStatus(ApiResponse.Status.INTERNAL_SERVICE_ERROR);
		}
		return ApiResponse.ofSuccess(null);
		*/
      //*****实现本地上传****end 
		
		//七牛云上传
		try {
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
		}
	}
	
	@GetMapping("/admin/house/list")
	public String houseListPage() {
		return "admin/house-list";
	}
}
