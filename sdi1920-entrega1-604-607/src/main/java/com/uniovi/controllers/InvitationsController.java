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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uniovi.entities.Invitation;
import com.uniovi.entities.User;
import com.uniovi.services.InvitationsService;
import com.uniovi.services.UsersService;

@Controller
public class InvitationsController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	
	@Autowired
	private InvitationsService invitationsService;

	@Autowired
	private UsersService usersService;

	@RequestMapping("invitation/list")
	public String getInvitations(Pageable pageable, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = usersService.findByEmail(auth.getName());
		
		Page<Invitation> invitations = new PageImpl<Invitation>(
				new LinkedList<Invitation>(user.getInvitations()));
		
		model.addAttribute("page", invitations);
		model.addAttribute("invitationsList", invitations.getContent());
		log.info("Listando las invitaciones del usuario {}.", user);
		return "invitations/list";
	}

	@RequestMapping("invitation/list/update")
	public String updateInvitations(Pageable pageable, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = usersService.findByEmail(auth.getName());
		
		Page<Invitation> invitations = new PageImpl<Invitation>(
				new LinkedList<Invitation>(user.getInvitations()));
		
		model.addAttribute("page", invitations);
		model.addAttribute("invitationsList", invitations.getContent());
		log.info("Actualizando la lista de invitaciones. ");
		return "invitations/list :: tableInvitations";
	}

	@RequestMapping("invitation/accept/{id}")
	public String acceptInvitation(@PathVariable Long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = usersService.findByEmail(auth.getName());

		invitationsService.acceptInvitation(user, id);
		log.info("El usuario {} acepta la invitacion con id {}.", user, id);

		return "redirect:/invitation/list";
	}
	
	@RequestMapping("invitation/deny/{id}")
	public String denyInvitation(@PathVariable Long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = usersService.findByEmail(auth.getName());

		invitationsService.denyInvitation(user, id);
		log.info("El usuario {} rechaza la invitacion con id {}.", user, id);

		return "redirect:/invitation/list";
	}
	
	@RequestMapping("invitation/send/{id}")
	public String sendInvitation(@PathVariable Long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User activeUser = usersService.findByEmail(auth.getName());
		User otherUser = usersService.getUser(id);
		
		invitationsService.sendInvitation(activeUser, otherUser);
		log.info("El usuario {} envía la invitacion con id {} al usuario {}.", activeUser, id, otherUser);

		return "redirect:/user/list";
	}
	
}
