package com.my.xunwu.web.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
}
