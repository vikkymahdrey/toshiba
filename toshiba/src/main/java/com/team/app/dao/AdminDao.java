package com.team.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team.app.domain.AdminUser;

public interface AdminDao extends JpaRepository<AdminUser, Integer> {

}
