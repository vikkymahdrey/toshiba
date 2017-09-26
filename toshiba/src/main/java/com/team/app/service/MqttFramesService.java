package com.team.app.service;

import java.util.List;

import com.team.app.domain.DownlinkQueue;
import com.team.app.domain.LoraFrame;

public interface MqttFramesService {

	void updateFrame(LoraFrame frame)throws Exception;

	List<LoraFrame> getFrames()throws Exception;

	List<LoraFrame> getFramesByLoraIdAndDevId(String loraId, String deviceId)throws Exception;

	List<LoraFrame> getFrameByDeviceId()throws Exception;

	List<LoraFrame> getFrameByDevId(String deviceId, String nodeName)throws Exception;

	void saveDownlink(DownlinkQueue q)throws Exception;

	List<DownlinkQueue> getDownlinkQueue()throws Exception;

	void deleteDownlinkQuere()throws Exception;

}
