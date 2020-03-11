package com.uniovi.controllers;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
	
	@RequestMapping("publication/list")
	public String getListPublication(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User usuarioSesion = usersService.findByEmail(auth.getName());
		model.addAttribute("publication", new Publication());
		model.addAttribute("publicationsList", publicationsService.getPublicationsOfUser(usuarioSesion));
		return "publications/list";
	}
	
	@RequestMapping(value = "publication/add", method = RequestMethod.POST)
	public String addPublication(Model model, @Validated Publication publication, BindingResult result,
				@RequestParam(required=false, value="photo") MultipartFile photo) {
		
		validator.validate(publication, result);
		if(result.hasErrors())
			return "publications/add";
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = usersService.findByEmail(auth.getName());
		
		publication.setPublicationDate(new Date());
		publication.setUser(user);
		
		publicationsService.addPublication(publication);
		
		if(photo != null)
			try {
				publicationsService.savePhoto(photo, publication.getId());
			} catch (IOException e) {
				e.printStackTrace();
				return "error";
			}
		// TODO: redireccionar a listar mis publicaciones
		return "redirect:/publication/list";
	}
	
}
