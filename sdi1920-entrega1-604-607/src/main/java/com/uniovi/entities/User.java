package com.uniovi.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "user")
public class User {
	
	@Id
	@GeneratedValue
	private long id;
	@Column(unique=true)
	private String email;
	private String name;
	private String lastName;
	private String password;
	@Transient // propiedad que no se almacena e la tabla. 
	private String passwordConfirm;
	private String role;
	
	@OneToMany(mappedBy = "user", cascade=CascadeType.ALL)
	private Set<Publication> publications;
	
	@OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL)
	private Set<Invitation> invitations;
	
	@OneToMany()
	private Set<User> friends;
	
	
	
	
	public Set<Publication> getPublications() {
		return publications;
	}


	public void setPublications(Set<Publication> publications) {
		this.publications = publications;
	}


	public User() {
		
	}
	
	
	public User(String email, String name, String lastName, String password, String passwordConfirm, String role) {
		super();
		this.email = email;
		this.name = name;
		this.lastName = lastName;
		this.password = password;
		this.passwordConfirm = passwordConfirm;
		this.role = role;
	}







	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordConfirm() {
		return passwordConfirm;
	}
	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}


	public Set<Invitation> getInvitations() {
		return invitations;
	}


	public void setInvitations(Set<Invitation> invitations) {
		this.invitations = invitations;
	}


	public Set<User> getFriends() {
		return friends;
	}


	public void setFriends(Set<User> friends) {
		this.friends = friends;
	}

	
	
	
}
