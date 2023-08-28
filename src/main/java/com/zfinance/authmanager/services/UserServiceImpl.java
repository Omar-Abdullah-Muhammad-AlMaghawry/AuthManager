package com.zfinance.authmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zfinance.authmanager.orm.User;
import com.zfinance.authmanager.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User getUserByLogin(String login) {
		return userRepository.findByLogin(login);
	}

	@Override
	public void save(User user) {
		userRepository.save(user);
	}

	@Override
	public User getUserByLoginAndEncPassword(String login, String encPassword) {
		return userRepository.findByLoginAndEncPassword(login, encPassword);
	}

}
