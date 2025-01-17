package com.uniovi.services;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Invitation;
import com.uniovi.entities.Publication;
import com.uniovi.entities.User;

@Service
public class InsertDataService {

	@Autowired
	private UsersService usersService;
	
	@Autowired
	private InvitationsService invitationsService;
	
	@Autowired
	private PublicationsService publicationService;
	
	@PostConstruct
	public void init() {
		
		
		User admin = new User("admin@email.com", "admin", "admin", "admin", "admin", "ROLE_ADMIN");
		User user = new User("usuario@email.com", "usuario", "usuario", "usuario", "usuario", "ROLE_USER");
		User thalia = new User("thalia@email.com", "Thalía", "Cuetos", "pass", "pass", "ROLE_USER");
		User sonia = new User("sonia@email.com", "Sonia", "García", "pass", "pass", "ROLE_USER");
		
		User user1= new User("usuario1@email.com", "usuario1", "usuario1", "usuario1", "usuario1", "ROLE_USER");
		User user2= new User("usuario2@email.com", "usuario2", "usuario2", "usuario2", "usuario2", "ROLE_USER");

		
		sonia.getFriends().add(thalia);
		sonia.getFriends().add(user);
		thalia.getFriends().add(sonia);
		user.getFriends().add(sonia);
		
		usersService.addUser(admin);
		usersService.addUser(thalia);
		usersService.addUser(user);
		usersService.addUser(sonia);
		usersService.addUser(user1);
		usersService.addUser(user2);

	
		
		
		

		Publication p= new Publication(new Date(), "Creación de la aplicación", "Prueba para las publicaciones", sonia);
		publicationService.addPublication(p);
		Publication p1= new Publication(new Date(), "Seguimos creando", "Prueba para las publicaciones", sonia);
		publicationService.addPublication(p1);
		
		Publication p2= new Publication(new Date(), "Thalía crea también la aplicación", "Prueba para las publicaciones de amigos", thalia);
		publicationService.addPublication(p2);
		
		
		invitationsService.addInvitation(new Invitation(admin, thalia));
	}
}
