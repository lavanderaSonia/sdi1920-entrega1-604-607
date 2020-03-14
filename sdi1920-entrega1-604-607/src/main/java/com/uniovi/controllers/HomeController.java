package com.uniovi.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());


	@RequestMapping("/")
	public String index() {
		log.info("Redirigiendo a index.");
		return "index";
	}
	
}
