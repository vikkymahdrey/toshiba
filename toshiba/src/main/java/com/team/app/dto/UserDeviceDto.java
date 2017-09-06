package com.team.app.dto;

import java.io.Serializable;
import java.util.Date;

public class UserDeviceDto extends BaseResponseDTO implements Serializable {

private static final long serialVersionUID = 1L;
	
	private String userId;
	private String deviceName;
	private String deviceType;
	private String deviceId;
	private String devVal;
	private String greenled;
	private String redled;
	private String devStatus;
	
	private String emailId;
	private String password;
	private String status;
	private String uname;
	private Date updateddt;
	private Date createddt;
	private String roleType;
	
	
	private String contactnumber;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getDevVal() {
		return devVal;
	}
	public void setDevVal(String devVal) {
		this.devVal = devVal;
	}
	public String getGreenled() {
		return greenled;
	}
	public void setGreenled(String greenled) {
		this.greenled = greenled;
	}
	public String getRedled() {
		return redled;
	}
	public void setRedled(String redled) {
		this.redled = redled;
	}
	public String getDevStatus() {
		return devStatus;
	}
	public void setDevStatus(String devStatus) {
		this.devStatus = devStatus;
	}
	public String getContactnumber() {
		return contactnumber;
	}
	public void setContactnumber(String contactnumber) {
		this.contactnumber = contactnumber;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public Date getUpdateddt() {
		return updateddt;
	}
	public void setUpdateddt(Date updateddt) {
		this.updateddt = updateddt;
	}
	public Date getCreateddt() {
		return createddt;
	}
	public void setCreateddt(Date createddt) {
		this.createddt = createddt;
	}
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	
	
	
}
