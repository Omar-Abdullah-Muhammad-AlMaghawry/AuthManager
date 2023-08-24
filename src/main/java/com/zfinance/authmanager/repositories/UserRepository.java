package com.zfinance.authmanager.repositories;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import com.zfinance.authmanager.orm.User;

public interface UserRepository extends CassandraRepository<User, String> {

	@Query("SELECT * FROM ZFIN_User WHERE contact.email = ?0")
	User findByContactEmail(String email);
}
