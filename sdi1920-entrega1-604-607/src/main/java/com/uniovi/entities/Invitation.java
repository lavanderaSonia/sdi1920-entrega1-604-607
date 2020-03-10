package com.uniovi.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;;

@Entity
@Table(name="invitation")
public class Invitation {
	
	
	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne
	private User applicant; //usuario que solicita la invitación de amistad
	
	@ManyToOne
	private User recipient; //usuario destinatario de esa solicitud
	
	public Invitation() {
		
	}
	

	public Invitation(User applicant, User recipient) {
		super();
		this.applicant = applicant;
		this.recipient = recipient;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getApplicant() {
		return applicant;
	}

	public void setApplicant(User applicant) {
		this.applicant = applicant;
	}


	public User getRecipient() {
		return recipient;
	}


	public void setRecipient(User recipient) {
		this.recipient = recipient;
	}
	
	
	

}
