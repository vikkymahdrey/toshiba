package com.team.app.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the tbl_device_info database table.
 * 
 */
@Entity
@Table(name="tbl_device_info")
@NamedQuery(name="TblDeviceInfo.findAll", query="SELECT t FROM TblDeviceInfo t")
public class TblDeviceInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createddt;

	@Column(name="device_name")
	private String deviceName;

	@Column(name="device_type")
	private String deviceType;

	private String deviceId;

	private String devVal;

	private String greenled;

	private String redled;

	private String status;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updateddt;

	//bi-directional many-to-many association to TblUserInfo
	@ManyToMany(mappedBy="tblDeviceInfos")
	private List<TblUserInfo> tblUserInfos;

	public TblDeviceInfo() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateddt() {
		return this.createddt;
	}

	public void setCreateddt(Date createddt) {
		this.createddt = createddt;
	}

	public String getDeviceName() {
		return this.deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceType() {
		return this.deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDevVal() {
		return this.devVal;
	}

	public void setDevVal(String devVal) {
		this.devVal = devVal;
	}

	public String getGreenled() {
		return this.greenled;
	}

	public void setGreenled(String greenled) {
		this.greenled = greenled;
	}

	public String getRedled() {
		return this.redled;
	}

	public void setRedled(String redled) {
		this.redled = redled;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getUpdateddt() {
		return this.updateddt;
	}

	public void setUpdateddt(Date updateddt) {
		this.updateddt = updateddt;
	}

	public List<TblUserInfo> getTblUserInfos() {
		return this.tblUserInfos;
	}

	public void setTblUserInfos(List<TblUserInfo> tblUserInfos) {
		this.tblUserInfos = tblUserInfos;
	}

}