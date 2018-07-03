package com.example.service;

import com.example.model.Role;
import com.example.model.User;

import java.util.Optional;

public interface UserService {
	User findUserByEmail(String email);
	Optional<User> findUserByName(String name);
	Optional<User> findUserById(Long id);
	void saveUser(User user);
	void saveRole(Role role);
}
