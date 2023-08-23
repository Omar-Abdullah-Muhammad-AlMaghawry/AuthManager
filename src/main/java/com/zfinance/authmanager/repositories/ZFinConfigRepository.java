package com.zfinance.authmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zfinance.authmanager.orm.ZFinConfig;

public interface ZFinConfigRepository extends JpaRepository<ZFinConfig, Long> {

}
