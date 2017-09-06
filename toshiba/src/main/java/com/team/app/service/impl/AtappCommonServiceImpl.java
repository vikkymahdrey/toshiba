package com.team.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.team.app.constant.AppConstants;
import com.team.app.dao.ToshibaKeyConfigDao;
import com.team.app.domain.TblToshibaKeyConfig;
import com.team.app.exception.AtAppException;
import com.team.app.service.AtappCommonService;
import com.team.app.utils.JWTKeyGenerator;

@Service("mightyCommonServiceImpl")
public class AtappCommonServiceImpl implements AtappCommonService {

	@Autowired
	private ToshibaKeyConfigDao atappKeyConfigDao;

	@Override
	public TblToshibaKeyConfig getKeyConfigByKey(String keyName) {
				return atappKeyConfigDao.getKeyConfigValue(keyName);
	}

	@Override
	public void validateXToken(String servicInvoker, String jwtToken) throws AtAppException {
		TblToshibaKeyConfig mightyConfig = getKeyConfigByKey(servicInvoker);
		
		if(mightyConfig == null || mightyConfig.getAtapp_key_value() == null) {
			throw new AtAppException("Invalid Service Invoker Value", HttpStatus.UNAUTHORIZED);
		}
		
		if(mightyConfig.getStatus().equalsIgnoreCase(AppConstants.IND_D)) {
			throw new AtAppException("Service Invoker Config is invalid", HttpStatus.NOT_IMPLEMENTED);
		}
		
		JWTKeyGenerator.validateJWTToken(mightyConfig.getAtapp_key_value(), jwtToken);
		
	}

}
