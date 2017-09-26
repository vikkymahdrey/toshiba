package com.team.app.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.team.app.domain.DownlinkQueue;

public interface DownlinkQueueDao  extends JpaRepository<DownlinkQueue, Serializable> {

	@Query("Select d From DownlinkQueue d")
	List<DownlinkQueue> getDownlinkQueue();

}
