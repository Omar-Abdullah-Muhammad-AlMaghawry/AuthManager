package com.zfinance.authmanager.services;

import com.zfinance.authmanager.orm.User;

public interface UserService {

	public User getUserByLogin(String login);

	public User getUserByLoginAndEncPassword(String login, String encPassword);

	void save(User user);

}
