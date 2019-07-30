package com.my.xunwu.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.xunwu.base.ApiResponse;

/**
 * 
 * @author hcz
 *	2019.7.25
 */
@Controller
public class HomeController {

	@GetMapping("/")
	public String index(Model model) {
		return "index";
	}
	
	@GetMapping("/404")
	public String notFoundPage() {
		return "404";
	}
	
	@GetMapping("/403")
	public String accessError() {
		return "403";
	}
	
	@GetMapping("/500")
	public String interalError() {
		return "500";
	}
	
	@GetMapping("/400")
	public String badRequest() {
		return "400";
	}
	
	@GetMapping("/logout")
	public String logoutPage() {
		return "logout";
	}
}
