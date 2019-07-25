package com.my.xunwu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 
 * @author hcz
 *	2019.7.25
 */
@Controller
public class HomeController {

	@GetMapping("/")
	public String index() {
		return "index";
	}
}
