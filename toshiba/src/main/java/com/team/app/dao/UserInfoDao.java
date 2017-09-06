package com.team.app.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.team.app.domain.TblUserInfo;

public interface UserInfoDao extends JpaRepository<TblUserInfo, Serializable> {

	@Query("Select u From TblUserInfo u where u.uname=:uname and u.password=:password and u.status='Y'")
	TblUserInfo getUserByUserAndPwd(@Param("uname") String uname,@Param("password") String password);

	@Query("Select count(u.id) From TblUserInfo u")
	List<TblUserInfo> getUserInfosCount();

	@Query("Select u From TblUserInfo u")
	List<TblUserInfo> getUserInfos();

	@Query("Select u From TblUserInfo u where u.id=:userId and u.status='Y'")
	TblUserInfo getUserById(@Param("userId") String userId);

	@Query("Select u From TblUserInfo u where u.emailId=:emailId and u.status='Y'")
	TblUserInfo getUserByEmailId(@Param("emailId") String emailId);
	
	

}
