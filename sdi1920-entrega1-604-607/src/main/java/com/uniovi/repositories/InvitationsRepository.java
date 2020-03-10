package com.uniovi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.uniovi.entities.Invitation;
import com.uniovi.entities.User;

public interface InvitationsRepository extends CrudRepository<Invitation, Long> {

	@Query("SELECT u FROM User u, Invitation i WHERE i.applicant=?1 AND u=i.recipient")
	List<User> getInvitedUsersBy(User applicant);

	@Query("SELECT u FROM User u, Invitation i WHERE i.recipient=?1 AND u=i.applicant")
	List<User> getUsersWhoInvited(User recipient);
	
}
