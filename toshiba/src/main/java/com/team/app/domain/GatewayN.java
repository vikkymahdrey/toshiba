package com.team.app.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the gateway_ns database table.
 * 
 */
@Entity
@Table(name="gateway_ns")
@NamedQuery(name="GatewayN.findAll", query="SELECT g FROM GatewayN g")
public class GatewayN implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String id;

	private String altitude;

	@Column(name="channel_configuration_id")
	private String channelConfigurationId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	private Date createdAt;

	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="first_seen_at")
	private Date firstSeenAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_seen_at")
	private Date lastSeenAt;

	private String location;

	private String mac;

	private String name;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_at")
	private Date updatedAt;

	public GatewayN() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAltitude() {
		return this.altitude;
	}

	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}

	public String getChannelConfigurationId() {
		return this.channelConfigurationId;
	}

	public void setChannelConfigurationId(String channelConfigurationId) {
		this.channelConfigurationId = channelConfigurationId;
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getFirstSeenAt() {
		return this.firstSeenAt;
	}

	public void setFirstSeenAt(Date firstSeenAt) {
		this.firstSeenAt = firstSeenAt;
	}

	public Date getLastSeenAt() {
		return this.lastSeenAt;
	}

	public void setLastSeenAt(Date lastSeenAt) {
		this.lastSeenAt = lastSeenAt;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getMac() {
		return this.mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

}