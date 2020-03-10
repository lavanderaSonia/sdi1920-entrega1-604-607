package com.uniovi.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.uniovi.entities.Publication;
import com.uniovi.repositories.PublicationsRepository;

@Service
public class PublicationsService {

	private String uploadFolder = "src/main/resources/static/files/";
	
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

	public void savePhoto(MultipartFile photo, Long id) throws IOException {
		byte[] bytes = photo.getBytes();		
		Path path = Paths.get(uploadFolder + id);
		Files.write(path,  bytes);
	}
}
