package com.uniovi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Publication;
import com.uniovi.repositories.PublicationsRepository;

@Service
public class PublicationsService {

	@Autowired
	private PublicationsRepository publicationsRepository;
	
	public void addPublication(Publication publication) {
		publicationsRepository.save(publication);
	}
	
	public Publication getPublication(Long id) {
		return publicationsRepository.findById(id).get();
	}
	
	public void deletePublication(Long id) {
		publicationsRepository.deleteById(id);
	}
}
