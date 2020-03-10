package com.uniovi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.uniovi.entities.User;
import com.uniovi.repositories.UsersRepository;

@Service
public class UsersService {

	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public User getUser(Long id) {
		return usersRepository.findById(id).get();
	}
	
	public List<User> getUsers() {
		List<User> users = new ArrayList<User>();
		usersRepository.findAll().forEach(u -> users.add(u));
		return users;
	}
	
	public Page<User> getUsers(String usuario, Pageable pageable) {
		return usersRepository.findAllUsers(usuario, pageable);
	}
	
	public Page<User> searchByNameLastNameAndEmail(Pageable pageable, String searchText){
		searchText = "%"+searchText+"%";
		return usersRepository.searchByNameLastNameAndEmail(pageable, searchText);
	}
	
	public void addUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		usersRepository.save(user);
	}
	
	public void deleteUser(Long id) {
		usersRepository.deleteById(id);
	}
	
	public User findByEmail(String email) {
		return usersRepository.findByEmail(email);
	}
	public User getUserByEmail(String email) {
		return usersRepository.findByEmail(email);
	}

	public Page<User> getFriends(String name, Pageable pageable) {
		return usersRepository.findAllFriends(pageable, name);
	}
	
}
