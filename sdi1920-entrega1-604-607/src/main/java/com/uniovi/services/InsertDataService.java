package com.uniovi.services;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Invitation;
import com.uniovi.entities.User;

@Service
public class InsertDataService {

	@Autowired
	private UsersService usersService;
	
	@Autowired
	private InvitationsService invitationsService;
	
	@PostConstruct
	public void init() {
		User admin = new User("admin@email.com", "admin", "admin", "admin", "admin", "ROLE_ADMIN");
		User thalia = new User("thalia@email.com", "Thalía", "Cuetos", "pass", "pass", "ROLE_USER");
		User sonia = new User("sonia@email.com", "Sonia", "García", "pass", "pass", "ROLE_USER");
		
		usersService.addUser(admin);
		usersService.addUser(sonia);
		usersService.addUser(thalia);
		usersService.addUser(new User("usuario@email.com", "usuario", "usuario", "usuario", "usuario", "ROLE_USER"));
	
		Invitation inv = new Invitation(sonia, thalia, false);
		invitationsService.addInvitation(inv);
		
	}
}
