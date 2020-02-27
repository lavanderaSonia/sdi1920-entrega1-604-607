package com.uniovi.repositories;

import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Invitation;

public interface InvitationsRepository extends CrudRepository<Invitation, Long>{

}
