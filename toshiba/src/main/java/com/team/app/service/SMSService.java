package com.team.app.service;

import com.team.app.domain.TblUserInfo;
import com.team.app.exception.AtAppException;

public interface SMSService {

	TblUserInfo sendLoginOtpToUser(TblUserInfo user) throws AtAppException;
	
}
