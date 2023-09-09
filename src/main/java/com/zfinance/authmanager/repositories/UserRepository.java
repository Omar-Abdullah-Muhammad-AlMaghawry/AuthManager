package com.zfinance.authmanager.repositories;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zfinance.authmanager.orm.User;

public interface UserRepository extends CassandraRepository<User, String> {

//	@Query("SELECT * FROM ZFIN_USER WHERE (contact.email = :P_LOGIN OR contact.phoneNumber = :P_LOGIN) ALLOW FILTERING")
	@Query("SELECT * FROM zfin_user WHERE (email = :p_login) ALLOW FILTERING")
	User findByLogin(@Param("p_login") String login);

	@Query("SELECT * FROM zfin_user "
//			+ "WHERE (contact.email = :P_LOGIN OR contact.phoneNumber = :P_LOGIN) ALLOW FILTERING")
			+ "WHERE (email = :p_login) " + "AND (enc_password = :P_enc_password) ALLOW FILTERING")
	User findByLoginAndEncPassword(@Param("p_login") String login, @Param("P_enc_password") String encPassword);

}
