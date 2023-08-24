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
	public User getUserByEmail(String email) {
		return userRepository.findByContactEmail(email);
	}

	@Override
	public void save(User user) {
		userRepository.save(user);
	}

}
