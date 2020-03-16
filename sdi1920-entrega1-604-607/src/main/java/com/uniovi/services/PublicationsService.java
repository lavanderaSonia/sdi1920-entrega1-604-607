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
		log.info("Añadida la publicación {} del usuario {}.", publication.getTitle(), publication.getUser().getName());
		publicationsRepository.save(publication);
	}
	
	public Publication getPublication(Long id) {
		return publicationsRepository.findById(id).get();
	}
	
	public void deletePublication(Long id) {
		publicationsRepository.deleteById(id);
	}

	public void savePhoto(MultipartFile photo, Publication publication) throws IOException {
		
		byte[] bytes = photo.getBytes();	
		if(bytes.length > 0) {
			publication.setHavePhoto(true);
			this.addPublication(publication);
			Path path = Paths.get(uploadFolder + publication.getId());
			Files.write(path,  bytes);
			log.info("Guardada la imagen para la publicación con ID {}.", publication.getId());
		} else
			this.addPublication(publication);
	}

	public List<Publication> getPublicationsOfUser(User name) {
		return publicationsRepository.findAllByUser(name);
	}
}
