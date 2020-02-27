package com.uniovi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.uniovi.services.UsersService;

@Controller
public class UsersController {

	@Autowired
	private UsersService usersService;
	
}
