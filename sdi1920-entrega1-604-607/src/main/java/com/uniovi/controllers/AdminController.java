package com.uniovi.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.User;
import com.uniovi.services.UsersService;

@Controller
public class AdminController {

	@Autowired
	private UsersService usersService;
	
	@RequestMapping("/admin/user/list")
	public String getListado(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User userActive = usersService.getUserByEmail(email);
		List<User> users = usersService.getUsers();
		if(users.remove(userActive))
			model.addAttribute("usersList", users);
		model.addAttribute("user", new User());
		return "user/listByAdmin";
	}
	
	@RequestMapping(value="/admin/user/delete", method = RequestMethod.POST)
	public String deleteUsers(@RequestParam("selected") List<Long> users) {
		Set<User> usuarios = new HashSet<User>();
		for(Long u: users) {
			usuarios.add(usersService.getUser(u));
		}
		
		for(User u: usuarios) {
			Set<User> friendsOfU= u.getFriends(); 
			for(User friend: friendsOfU) {  
				friend.getFriends().remove(u); 
				friendsOfU.remove(friend);
			}
		}
		for(User u: usuarios)
			usersService.deleteUser(u.getId());
		return "redirect:/admin/user/list";
	}
}
