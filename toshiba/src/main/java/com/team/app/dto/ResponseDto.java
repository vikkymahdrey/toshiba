package com.team.app.dto;

import java.io.Serializable;

public class ResponseDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	private String statusDesc;
	private String jwt;
	public String getJwt() {
		return jwt;
	}
	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
}
