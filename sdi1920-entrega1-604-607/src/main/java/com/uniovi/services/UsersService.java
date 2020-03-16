package com.uniovi.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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

	public List<User> getUsersWithoutUser(User user) {
		List<User> users = new ArrayList<User>();
		usersRepository.findAll().forEach(u -> users.add(u));
		users.remove(user);
		return users;
	}

	public Page<User> getUsers(String usuario, Pageable pageable) {
		return usersRepository.findAllUsers(usuario, pageable);
	}

	public Page<User> searchByNameLastNameAndEmail(Pageable pageable, String searchText) {
		searchText = "%" + searchText + "%";
		return usersRepository.searchByNameLastNameAndEmail(pageable, searchText);
	}

	public void addUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		usersRepository.save(user);
	}

	public List<User> deleteUsers(List<Long> ids) {
		Set<User> usuarios = new HashSet<User>();
		for (Long u : ids) {
			usuarios.add(getUser(u));
		}

		Iterator<User> itu = usuarios.iterator();
		while (itu.hasNext()) {
			User u = itu.next();
			Set<User> friendsOfU = u.getFriends();
			Iterator<User> it = friendsOfU.iterator();
			while (it.hasNext()) {
				User friend = it.next();
				friend.getFriends().remove(u);
				it.remove();
			}
		}
		List<User> users = new ArrayList<User>();
		for (User u : usuarios)
			users.add(u);
		return users;
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
