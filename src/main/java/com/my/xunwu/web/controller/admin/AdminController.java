package com.my.xunwu.web.controller.admin;


import java.io.File;
import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.my.xunwu.base.ApiResponse;

/**
 * 
 * @author huangcaizhuan
 *2019.7.30
 */
@Controller
public class AdminController {

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
        String fileName = file.getOriginalFilename();
        File target = new File("F:/test/xunwu/images/"+fileName);
        try {
			file.transferTo(target);
		} catch (IOException e) {
			e.printStackTrace();
			return ApiResponse.ofStatus(ApiResponse.Status.INTERNAL_SERVICE_ERROR);
		}
		return ApiResponse.ofSuccess(null);
	}
	
	@GetMapping("/admin/house/list")
	public String houseListPage() {
		return "admin/house-list";
	}
}
