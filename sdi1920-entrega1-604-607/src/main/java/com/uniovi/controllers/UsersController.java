package com.uniovi.controllers;

import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.uniovi.services.InvitationsService;
import com.uniovi.services.RolesService;
import com.uniovi.services.SecurityService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.SignUpFormValidator;

@Controller
public class UsersController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UsersService usersService;

	@Autowired
	private SignUpFormValidator signUpFormValidator;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private RolesService rolesService;

	@Autowired
	private InvitationsService invitationsService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		log.info("Solicitando la página de login");
		return "login";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		model.addAttribute("user", new User());
		log.info("Solicitando la página de registro");
		return "signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(@ModelAttribute @Validated User user, BindingResult result) {
		signUpFormValidator.validate(user, result);
		if (result.hasErrors()) {
			log.info("El usuario no ha podido ser registrado porque alguno de sus datos presentes en el "
					+ "formulario de registro eran incorrectos.");
			return "signup";
		}
		user.setRole(rolesService.getRoles()[1]);
		usersService.addUser(user);
		securityService.autoLogin(user.getEmail(), user.getPasswordConfirm());
		log.info("El usuario {} ha sido registrado de forma correcta. ", user);
		return "redirect:home";
	}

	@RequestMapping("/user/list")
	public String getListado(Model model, Pageable pageable,
			@RequestParam(value = "", required = false) String searchText) {
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User userActive = usersService.getUserByEmail(email);
		if (searchText != null && !searchText.isEmpty())
			users = usersService.searchByNameLastNameAndEmail(pageable, searchText);
		else {
			users = usersService.getUsers(userActive.getName(), pageable);
		}

		model.addAttribute("invitedUsers", invitationsService.getInvitedUsersBy(userActive));
		model.addAttribute("friends", userActive.getFriends());
		model.addAttribute("otherUsers", invitationsService.getUsersWhoInvited(userActive));
		
		model.addAttribute("usersList", users.getContent());
		model.addAttribute("page", users);
		log.info("Se están listando los usuarios de la apliación por petición del usuario {}.", email);
		return "user/list";
	}

	@RequestMapping("/user/friends/list")
	public String getListadoAmigos(Model model, Pageable pageable) {
		Page<User> users = new PageImpl<User>(new LinkedList<User>());

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User userActive = usersService.getUserByEmail(email);
		users = usersService.getFriends(userActive.getName(), pageable);

		model.addAttribute("friendsList", users.getContent());
		model.addAttribute("page", users);
		log.info("Se están listando los amigos del usuario {}. ", email);
		return "friends/list";
	}

	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public String home(Model model) {
		log.info("Redirigiendo a la página home");
		return "home";
	}

}
