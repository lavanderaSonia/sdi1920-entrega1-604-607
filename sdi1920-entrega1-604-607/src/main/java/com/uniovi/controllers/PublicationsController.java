package com.uniovi.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniovi.entities.Publication;
import com.uniovi.entities.User;
import com.uniovi.services.PublicationsService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.AddPublicationFormValidator;

@Controller
public class PublicationsController {

	@Autowired
	private PublicationsService publicationsService;
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private AddPublicationFormValidator validator;
	
	@RequestMapping("publication/add")
	public String getAddPublication(Model model) {
		model.addAttribute("publication", new Publication());
		return "publications/add";
	}
	
	@RequestMapping(value = "publication/add", method = RequestMethod.POST)
	public String addPublication(Model model, @Validated Publication publication, BindingResult result) {
		validator.validate(publication, result);
		if(result.hasErrors())
			return "publications/add";

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = usersService.findByEmail(auth.getName());
		
		publication.setPublicationDate(new Date());
		publication.setUser(user);
		
		publicationsService.addPublication(publication);
		// TODO: redireccionar a listar mis publicaciones
		return "home";
	}
	
}
