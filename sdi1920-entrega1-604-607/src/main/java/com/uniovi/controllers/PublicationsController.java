package com.uniovi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.uniovi.services.PublicationsService;

@Controller
public class PublicationsController {

	@Autowired
	private PublicationsService publicationsService;
	
}
