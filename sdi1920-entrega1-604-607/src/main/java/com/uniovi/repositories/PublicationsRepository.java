package com.uniovi.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Publication;
import com.uniovi.entities.User;

public interface PublicationsRepository extends CrudRepository<Publication, Long> {

	List<Publication> findAllByUser(User user);

}
