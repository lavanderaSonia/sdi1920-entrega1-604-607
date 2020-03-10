package com.uniovi.controllers;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.User;
import com.uniovi.services.RolesService;
import com.uniovi.services.SecurityService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.SignUpFormValidator;

@Controller
public class UsersController {

	@Autowired
	private UsersService usersService;



	@Autowired
	private SignUpFormValidator signUpFormValidator;

	@Autowired
	private SecurityService securityService;

	
	@Autowired
	private RolesService rolesService;
	
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		return "login";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(@ModelAttribute @Validated User user, BindingResult result) {
		signUpFormValidator.validate(user, result);
		if (result.hasErrors()) {
			return "signup";
		}
		user.setRole(rolesService.getRoles()[1]);
		usersService.addUser(user);
		securityService.autoLogin(user.getEmail(), user.getPasswordConfirm());
		return "redirect:home";
	}

	
	@RequestMapping("/user/list")
	public String getListado(Model model, Pageable pageable,@RequestParam(value="", required=false) String searchText) {
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		if(searchText!=null && !searchText.isEmpty())
			users = usersService.searchByNameLastNameAndEmail(pageable, searchText);
		else {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String email = auth.getName();
			User userActive = usersService.getUserByEmail(email);
			users=usersService.getUsers(userActive.getName(), pageable);
		}
		model.addAttribute("usersList", users.getContent()); 
		model.addAttribute("page", users);
		return "user/list";
	}
	
	
	@RequestMapping("/user/friends/list")
	public String getListadoAmigos(Model model, Pageable pageable) {
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User userActive = usersService.getUserByEmail(email);
		users=usersService.getFriends(userActive.getName(), pageable);
		
		
		model.addAttribute("friendsList", users.getContent()); 
		model.addAttribute("page", users);
		return "friends/list";
	}
	
	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public String home(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User userActive = usersService.getUserByEmail(email);
		//model.addAttribute("markList", userActive.getMarks());
		return "home";
	}
	
	

}
