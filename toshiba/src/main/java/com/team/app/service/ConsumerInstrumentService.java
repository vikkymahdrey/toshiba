package com.team.app.service;

import java.util.List;

import com.team.app.domain.JwtToken;
import com.team.app.domain.TblUserInfo;
import com.team.app.domain.User;
import com.team.app.dto.UserLoginDTO;
import com.team.app.exception.AtAppException;

/**
 * 
 * @author Vikky
 *
 */
public interface ConsumerInstrumentService {
	
	public UserLoginDTO mobileLoginAuth(String username, String password) throws AtAppException;
	public UserLoginDTO getRefreshTokenOnBaseToken()throws AtAppException;
	public List<TblUserInfo> getUserInfosCount()throws AtAppException;
	public List<TblUserInfo> getUserInfos()throws AtAppException;
	public TblUserInfo getUserById(String userId)throws AtAppException;
	public TblUserInfo updateUser(TblUserInfo userInfo)throws AtAppException;
	public TblUserInfo getUserByEmailId(String emailId)throws AtAppException;

	public List<JwtToken> getJwtToken()throws Exception;

	public void updateJwt(JwtToken jwtT)throws Exception;
	
	public User getNSUserById(String usrId)throws Exception;
	
	public void updateNSUser(User u)throws Exception;
	
}
