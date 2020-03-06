package com.uniovi.services;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.User;

@Service
public class InsertDataService {

	@Autowired
	private UsersService usersService;
	
	@PostConstruct
	public void init() {
		User admin = new User("admin@email.com", "admin", "admin", "admin", "admin", "ROLE_ADMIN");
		
		usersService.addUser(admin);
	}
}
