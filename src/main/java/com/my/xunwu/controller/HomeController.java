package com.my.xunwu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
