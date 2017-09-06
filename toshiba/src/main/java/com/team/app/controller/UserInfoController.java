package com.team.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.team.app.domain.TblUserInfo;
import com.team.app.dto.UserDeviceDto;
import com.team.app.logger.AtLogger;
import com.team.app.service.ConsumerInstrumentService;

@Controller
public class UserInfoController {
	
	private static final AtLogger logger = AtLogger.getLogger(UserInfoController.class);
	
	@Autowired
	private ConsumerInstrumentService consumerInstrumentServiceImpl;
	
	@RequestMapping(value= {"/userInfoHistory"}, method=RequestMethod.GET)
    public String userInfoHistoryHandler(Map<String,Object> map) {
		
			List<UserDeviceDto> dtoList=null;
			UserDeviceDto dto=null;
			List<TblUserInfo> userInfos=consumerInstrumentServiceImpl.getUserInfos();
			
			if(userInfos!=null && userInfos.isEmpty()){
				for(TblUserInfo u: userInfos){
					dto.setUname(u.getUname());
					
					
				}
			}
				map.put("userInfos", userInfos);
					return "userInfo";
		 
	 }

}
