package com.zfinance.authmanager.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.zfinance.authmanager.orm.ZFinConfig;

public interface ZFinConfigRepository extends MongoRepository<ZFinConfig, String> {

	ZFinConfig findByCode(String code);

}
