package com.zfinance.authmanager.repositories;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zfinance.authmanager.orm.User;

public interface UserRepository extends CassandraRepository<User, String> {

	@Query("SELECT * FROM ZFIN_USER WHERE (contact.email = :P_LOGIN OR contact.phoneNumber = :P_LOGIN) ALLOW FILTERING")
	User findByLogin(@Param("P_LOGIN") String login);

	@Query("SELECT * FROM ZFIN_USER "
			+ "WHERE (contact.email = :P_LOGIN OR contact.phoneNumber = :P_LOGIN) ALLOW FILTERING")
//			+ "AND (password = :P_ENC_PASSWORD )")
	User findByLoginAndEncPassword(@Param("P_LOGIN") String login, @Param("P_ENC_PASSWORD") String encPassword);

}
