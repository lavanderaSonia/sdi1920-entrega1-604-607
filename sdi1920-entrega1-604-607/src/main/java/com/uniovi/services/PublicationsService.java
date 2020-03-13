package com.uniovi.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.uniovi.entities.Publication;
import com.uniovi.entities.User;
import com.uniovi.repositories.PublicationsRepository;

@Service
public class PublicationsService {

	private String uploadFolder = "src/main/resources/static/files/";
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PublicationsRepository publicationsRepository;
	
	public void addPublication(Publication publication) {
		publicationsRepository.save(publication);
		log.info("El usuario {} ha creado la publicación {}.", publication.getUser().getEmail(),
				publication.getTitle());
	}
	
	public Publication getPublication(Long id) {
		return publicationsRepository.findById(id).get();
	}
	
	public void deletePublication(Long id) {
		publicationsRepository.deleteById(id);
	}

	@Transactional
	public void savePhoto(MultipartFile photo, Publication publication) throws IOException {
		publication.setHavePhoto(true);
		this.addPublication(publication);
		
		log.info("Imagen añadida a publicación con id {}.", publication.getId());
		
		byte[] bytes = photo.getBytes();		
		Path path = Paths.get(uploadFolder + publication.getId());
		Files.write(path,  bytes);
	}

	public List<Publication> getPublicationsOfUser(User name) {
		return publicationsRepository.findAllByUser(name);
	}
}
