package com.uniovi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uniovi.entities.Invitation;
import com.uniovi.entities.User;
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

	@Transactional
	public void acceptInvitation(User user, Long id) {
		Invitation invitation = getInvitation(id);
		if(!invitation.getRecipient().equals(user))
			return;
		
		invitation.getRecipient().getFriends().add(invitation.getApplicant());
		invitation.getApplicant().getFriends().add(invitation.getRecipient());
		deleteInvitation(id);
	}

	public void denyInvitation(User user, Long id) {
		Invitation invitation = getInvitation(id);
		if(!invitation.getRecipient().equals(user))
			return;
		
		deleteInvitation(id);
	}

	public List<User> getInvitedUsersBy(User applicant) {
		return invitationsRepository.getInvitedUsersBy(applicant);
	}
	
	public List<User> getUsersWhoInvited(User recipient) {
		return invitationsRepository.getUsersWhoInvited(recipient);
	}

	public void sendInvitation(User from, User to) {
		if(getInvitedUsersBy(from).contains(to) || from.getFriends().contains(to)
				|| getUsersWhoInvited(from).contains(to))
			return;
		
		Invitation inv = new Invitation(from, to);
		to.getInvitations().add(inv);
		addInvitation(inv);
	}
}
