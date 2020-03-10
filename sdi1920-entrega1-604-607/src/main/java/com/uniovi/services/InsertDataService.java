package com.uniovi.services;

import java.util.HashSet;
import java.util.Set;

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
		User user = new User("usuario@email.com", "usuario", "usuario", "usuario", "usuario", "ROLE_USER");
		User thalia = new User("thalia@email.com", "Thalía", "Cuetos", "pass", "pass", "ROLE_USER");
		
		

		
		usersService.addUser(admin);
		usersService.addUser(thalia);
		usersService.addUser(user);
		
		Set<User> friendsOfSonia = new HashSet<User>();
		friendsOfSonia.add(thalia);
		friendsOfSonia.add(user);
		
		User sonia = new User("sonia@email.com", "Sonia", "García", "pass", "pass", "ROLE_USER", friendsOfSonia);
		
		usersService.addUser(sonia);
		
		thalia.getFriends().add(sonia);
		user.getFriends().add(sonia);
		
		invitationsService.addInvitation(new Invitation(admin, thalia));
		invitationsService.addInvitation(new Invitation(user, thalia));		
	}
}
