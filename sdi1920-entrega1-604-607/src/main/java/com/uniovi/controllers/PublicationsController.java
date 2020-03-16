package com.uniovi.controllers;

import java.io.IOException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());


	@Autowired
	private PublicationsService publicationsService;

	@Autowired
	private UsersService usersService;

	@Autowired
	private AddPublicationFormValidator validator;

	@RequestMapping("publication/add")
	public String getAddPublication(Model model) {
		model.addAttribute("publication", new Publication());
		log.info("Solicitando el formulario para añadir una publiación");
		return "publications/add";
	}

	

	@RequestMapping("publication/list/{id}")
	public String getListPublicationsOfFriend(Model model, @PathVariable Long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User usuarioSesion = usersService.findByEmail(auth.getName());
		//Este es el amigo
		User amigo = usersService.getUser(id);
		if(usuarioSesion.getFriends().contains(amigo)) {
		model.addAttribute("publication", new Publication());
		model.addAttribute("publicationsList", publicationsService.getPublicationsOfUser(amigo));
		log.info("Listando las publicaciones del usuario {}, que es amigo del usuario {}. ", amigo, usuarioSesion);
		return "publications/publicationsFriend";

		}
		else {
			log.info("El usuario está intentando acceder a las publicaciones de un usuario que no es su amigo");
			return "error";
		}
	}
	
	@RequestMapping("publication/list")
	public String getListPublication(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User usuarioSesion = usersService.findByEmail(auth.getName());
		model.addAttribute("publication", new Publication());
		model.addAttribute("publicationsList", publicationsService.getPublicationsOfUser(usuarioSesion));
		log.info("Listando las publicaciones del usuario {}. ", usuarioSesion);
		return "publications/list";
	}

	@RequestMapping(value = "publication/add", method = RequestMethod.POST)
	public String addPublication(Model model, @Validated Publication publication, BindingResult result,
			@RequestParam(required = false, value = "photo") MultipartFile photo) {

		validator.validate(publication, result);
		if (result.hasErrors()) {
			log.info("Hay errores a la hora de crear la publicación");
			return "publications/add";
		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = usersService.findByEmail(auth.getName());

		publication.setPublicationDate(new Date());
		publication.setUser(user);

		if (photo != null)
			try {
				publicationsService.savePhoto(photo, publication);
				log.info("Se guarda la foto de la publicación");
			} catch (IOException e) {
				e.printStackTrace();
				return "error";
			}
		else {
			publicationsService.addPublication(publication);
			log.info("Se añade la publicación");
		}
		// Redireccionar a listar mis publicaciones
		return "redirect:/publication/list";
	}

}
