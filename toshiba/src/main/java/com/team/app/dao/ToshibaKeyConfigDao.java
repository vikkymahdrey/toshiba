package com.team.app.dao;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.team.app.domain.TblToshibaKeyConfig;

public interface ToshibaKeyConfigDao extends JpaRepository<TblToshibaKeyConfig, Serializable> {
	
	@Query("Select key From TblToshibaKeyConfig key where key.atapp_key_name=:keyMightyMobile")
	TblToshibaKeyConfig getKeyConfigValue(@Param("keyMightyMobile") String keyMightyMobile);

}
