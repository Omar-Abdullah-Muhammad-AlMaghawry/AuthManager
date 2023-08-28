package com.zfinance.authmanager.repositories;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zfinance.authmanager.orm.ZFinConfig;

public interface ZFinConfigRepository extends CassandraRepository<ZFinConfig, Long> {

	@Query("SELECT * FROM zfin_config WHERE (code = :p_code) ALLOW FILTERING")
	ZFinConfig findByCode(@Param("p_code") String code);

}
