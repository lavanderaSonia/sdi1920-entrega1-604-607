package com.uniovi.entities;

import javax.persistence.*;

@Entity
@Table(name = "relationship")
public class Relationship {
	
	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private User friend;
	
	public Relationship() {
		
	}

	public Relationship(User user, User friend) {
		super();
		this.user = user;
		this.friend = friend;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getFriend() {
		return friend;
	}

	public void setFriend(User friend) {
		this.friend = friend;
	}
	
	
	
	
	
	

}
