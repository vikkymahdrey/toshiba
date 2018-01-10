package com.team.app.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the lora_frames database table.
 * 
 */
@Entity
@Table(name="lora_frames")
@NamedQuery(name="LoraFrame.findAll", query="SELECT l FROM LoraFrame l")
public class LoraFrame extends BaseEntityInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String id;

	private String applicationID;

	private String applicationName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	private Date createdAt;

	private String devEUI;

	private String deviceId;

	private String fPort;

	@Column(name="gateway_mac")
	private String gatewayMac;

	@Column(name="gateway_name")
	private String gatewayName;

	private String humidity;

	private String led1;

	private String led2;

	private String led3;

	private String led4;
	
	private String peripheral;
	private String central;

	

	public String getPeripheral() {
		return peripheral;
	}

	public void setPeripheral(String peripheral) {
		this.peripheral = peripheral;
	}

	public String getCentral() {
		return central;
	}

	public void setCentral(String central) {
		this.central = central;
	}

	private String nodeName;

	private String pressure;
	
	private String loraId;
	
	private String length;

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	private String temperature;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_at")
	private Date updatedAt;

	public LoraFrame() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getApplicationID() {
		return this.applicationID;
	}

	public void setApplicationID(String applicationID) {
		this.applicationID = applicationID;
	}

	public String getApplicationName() {
		return this.applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getDevEUI() {
		return this.devEUI;
	}

	public void setDevEUI(String devEUI) {
		this.devEUI = devEUI;
	}

	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	

	public String getfPort() {
		return fPort;
	}

	public void setfPort(String fPort) {
		this.fPort = fPort;
	}

	public String getLoraId() {
		return loraId;
	}

	public void setLoraId(String loraId) {
		this.loraId = loraId;
	}

	public String getGatewayMac() {
		return this.gatewayMac;
	}

	public void setGatewayMac(String gatewayMac) {
		this.gatewayMac = gatewayMac;
	}

	public String getGatewayName() {
		return this.gatewayName;
	}

	public void setGatewayName(String gatewayName) {
		this.gatewayName = gatewayName;
	}

	public String getHumidity() {
		return this.humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getLed1() {
		return this.led1;
	}

	public void setLed1(String led1) {
		this.led1 = led1;
	}

	public String getLed2() {
		return this.led2;
	}

	public void setLed2(String led2) {
		this.led2 = led2;
	}

	public String getLed3() {
		return this.led3;
	}

	public void setLed3(String led3) {
		this.led3 = led3;
	}

	public String getLed4() {
		return this.led4;
	}

	public void setLed4(String led4) {
		this.led4 = led4;
	}

	public String getNodeName() {
		return this.nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getPressure() {
		return this.pressure;
	}

	public void setPressure(String pressure) {
		this.pressure = pressure;
	}

	public String getTemperature() {
		return this.temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public Date getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

}