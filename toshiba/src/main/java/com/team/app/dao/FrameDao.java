package com.team.app.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.team.app.domain.LoraFrame;

public interface FrameDao extends JpaRepository<LoraFrame, Serializable> {

	@Query("Select f from LoraFrame f order by id desc")
	List<LoraFrame> getFrames();

	@Query(value="Select * from lora_frames f where f.nodeName=?1 and f.DeviceId=?2  ORDER BY id DESC LIMIT 10",nativeQuery = true)
	List<LoraFrame> getFramesByLoraIdAndDevId(@Param("loraId") String loraId, @Param("deviceId") String deviceId);

	/*@Query(value="Select * from lora_frames f group by f.DeviceId",nativeQuery = true)
	List<LoraFrame> getFrameByDeviceId();*/
	
	@Query(value="Select f from LoraFrame f group by f.deviceId,f.nodeName")
	List<LoraFrame> getFrameByDeviceId();

	@Query("Select f from LoraFrame f where f.deviceId=:deviceId and f.nodeName=:nodeName order by id desc")
	List<LoraFrame> getFrameByDevId(@Param("deviceId") String deviceId,@Param("nodeName") String nodeName);


}
