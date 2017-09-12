package com.team.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.team.app.domain.LoraFrame;
import com.team.app.domain.TblUserInfo;
import com.team.app.dto.UserDeviceDto;
import com.team.app.logger.AtLogger;
import com.team.app.service.ConsumerInstrumentService;
import com.team.app.service.MqttFramesService;

@Controller
public class UserInfoController {
	
	private static final AtLogger logger = AtLogger.getLogger(UserInfoController.class);
	
	@Autowired
	private ConsumerInstrumentService consumerInstrumentServiceImpl;
		
	@Autowired
	private MqttFramesService mqttFramesService;
	
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
	
	
	@SuppressWarnings("unused")
	@RequestMapping(value= {"/frameInfos"}, method=RequestMethod.GET)
    public String framesInfoHandler(Map<String,Object> map) {
		
			logger.debug("/inside framesInfo");
			List<LoraFrame> frames=null;
			try{
				frames=mqttFramesService.getFrames();
				
				if(frames!=null && !frames.isEmpty()){
					logger.debug("size is ",frames.size());
					map.put("frames", frames);
				}
			
			}catch(Exception e){
				logger.error("/frameInfos controller ",e);
				e.printStackTrace();
			}
					return "frames";
		 
	 }

}
