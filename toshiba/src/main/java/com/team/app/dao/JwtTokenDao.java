package com.team.app.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.team.app.domain.JwtToken;

public interface JwtTokenDao extends JpaRepository<JwtToken, Serializable> {

	@Query("From JwtToken")
	List<JwtToken> getJwtToken();

}
