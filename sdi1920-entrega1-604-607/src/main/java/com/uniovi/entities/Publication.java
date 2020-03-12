package com.uniovi.entities;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "publication")
public class Publication {

	@Id
	@GeneratedValue
	private Long id;

	private Date publicationDate;

	private String title;
	private String text;
	private boolean havePhoto;
	
	@ManyToOne
	private User user;

	public Publication() {
	}

	public Publication(Date publicationDate, String title, String text, User user) {
		super();
		this.publicationDate = publicationDate;
		this.title = title;
		this.text = text;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public String dateToString() {
		String strDateFormat = "dd/MM/yyyy"; // El formato de fecha está especificado  
        SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat); // La cadena de formato de fecha se pasa como un argumento al objeto 
        return objSDF.format(getPublicationDate());
	}

	
	public boolean getHavePhoto() {
		return havePhoto;
	}
	
	public void setHavePhoto(boolean photo) {
		this.havePhoto = photo;
	}
}
