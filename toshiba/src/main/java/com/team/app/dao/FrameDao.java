package com.team.app.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.team.app.domain.LoraFrame;

public interface FrameDao extends JpaRepository<LoraFrame, Serializable> {

	@Query("Select f from LoraFrame f")
	List<LoraFrame> getFrames();

	@Query(value="Select * from lora_frames f where f.loraId=?1 and f.DeviceId=?2  ORDER BY id DESC LIMIT 10",nativeQuery = true)
	List<LoraFrame> getFramesByLoraIdAndDevId(@Param("loraId") String loraId, @Param("deviceId") String deviceId);

}
