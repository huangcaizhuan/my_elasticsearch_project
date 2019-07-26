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
		model.addAttribute("name","环游记");
		return "index";
	}
	
	@GetMapping("/get")
	@ResponseBody
	public ApiResponse get() {
		return ApiResponse.ofMessage(200, "OK");
	}
}
