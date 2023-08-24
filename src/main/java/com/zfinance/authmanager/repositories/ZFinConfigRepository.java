package com.zfinance.authmanager.repositories;

import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.zfinance.authmanager.orm.ZFinConfig;

public interface ZFinConfigRepository extends CassandraRepository<ZFinConfig, Long> {

	Optional<ZFinConfig> findByCode(String code);

}
