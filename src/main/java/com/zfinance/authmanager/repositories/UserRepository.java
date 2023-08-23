package com.zfinance.authmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zfinance.authmanager.orm.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
