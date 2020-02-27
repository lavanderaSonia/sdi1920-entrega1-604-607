package com.uniovi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.uniovi.services.InvitationsService;

@Controller
public class InvitationsController {

	@Autowired
	private InvitationsService invitationsService;
	
}
