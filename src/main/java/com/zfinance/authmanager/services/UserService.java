package com.zfinance.authmanager.services;

import com.zfinance.authmanager.orm.User;

public interface UserService {

	User getUserByEmail(String userName);

}
