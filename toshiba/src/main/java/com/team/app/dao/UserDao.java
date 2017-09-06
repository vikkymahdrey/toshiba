package com.team.app.dao;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.team.app.domain.User;

public interface UserDao extends JpaRepository<User, Serializable> {

	@Query("Select u From User u where u.id=:id and u.isActive='true'")
	User getNSUserById(@Param("id") String id);


}
