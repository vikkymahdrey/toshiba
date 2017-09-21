package com.team.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team.app.dao.FrameDao;
import com.team.app.domain.LoraFrame;
import com.team.app.logger.AtLogger;
import com.team.app.service.MqttFramesService;

@Service
public class MqttFramesServiceImpl implements MqttFramesService {

	private static final AtLogger logger = AtLogger.getLogger(MqttFramesServiceImpl.class);
	@Autowired
	private FrameDao frmaeDao;

	
	public void updateFrame(LoraFrame frame) throws Exception {
		logger.debug("inside MqttFramesServiceImpl save frame");
		 frmaeDao.save(frame);
	}


	
	public List<LoraFrame> getFrames() throws Exception {
		return frmaeDao.getFrames();
	}



	
	public List<LoraFrame> getFramesByLoraIdAndDevId(String loraId, String deviceId) throws Exception {
		return frmaeDao.getFramesByLoraIdAndDevId(loraId,deviceId);
	}




	public List<LoraFrame> getFrameByDeviceId() throws Exception {
	
		return frmaeDao.getFrameByDeviceId();
	}



	public List<LoraFrame> getFrameByDevId(String deviceId,String nodeName) throws Exception {
		return frmaeDao.getFrameByDevId(deviceId,nodeName);
	}





	
}
