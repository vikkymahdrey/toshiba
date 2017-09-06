package com.team.app.service;

import com.team.app.domain.TblToshibaKeyConfig;
import com.team.app.exception.AtAppException;

public interface AtappCommonService {

	public TblToshibaKeyConfig getKeyConfigByKey(String keyName);
	
	public void validateXToken(String servicInvoker, String jwtToken) throws AtAppException;
}
