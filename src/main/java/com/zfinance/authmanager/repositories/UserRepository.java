package com.zfinance.authmanager.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import com.zfinance.authmanager.orm.User;

public interface UserRepository extends MongoRepository<User, String> {

	User findByEmail(@Param("p_login") String login);

	User findByEmailAndPartnerId(String login, String partnerId);

	User findByEmailAndEncPassword(String login, String encPassword);

}
