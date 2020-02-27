package com.uniovi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Invitation;
import com.uniovi.repositories.InvitationsRepository;

@Service
public class InvitationsService {

	@Autowired
	private InvitationsRepository invitationsRepository;
	
	public Invitation getInvitation(Long id) {
		return invitationsRepository.findById(id).get();
	}
	
	public void addInvitation(Invitation invitation) {
		invitationsRepository.save(invitation);
	}
	
	public void deleteInvitation(Long id) {
		invitationsRepository.deleteById(id);
	}
}
