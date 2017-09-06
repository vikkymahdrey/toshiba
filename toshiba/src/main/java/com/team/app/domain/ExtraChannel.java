package com.team.app.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the extra_channel database table.
 * 
 */
@Entity
@Table(name="extra_channel")
@NamedQuery(name="ExtraChannel.findAll", query="SELECT e FROM ExtraChannel e")
public class ExtraChannel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String id;

	private String bandwidth;

	@Column(name="bit_rate")
	private String bitRate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	private Date createdAt;

	private String frequency;

	private String modulation;

	@Column(name="spread_factors")
	private String spreadFactors;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_at")
	private Date updatedAt;

	//bi-directional many-to-one association to ChannelConfiguration
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="channel_configuration_id")
	private ChannelConfiguration channelConfiguration;

	public ExtraChannel() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBandwidth() {
		return this.bandwidth;
	}

	public void setBandwidth(String bandwidth) {
		this.bandwidth = bandwidth;
	}

	public String getBitRate() {
		return this.bitRate;
	}

	public void setBitRate(String bitRate) {
		this.bitRate = bitRate;
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getFrequency() {
		return this.frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getModulation() {
		return this.modulation;
	}

	public void setModulation(String modulation) {
		this.modulation = modulation;
	}

	public String getSpreadFactors() {
		return this.spreadFactors;
	}

	public void setSpreadFactors(String spreadFactors) {
		this.spreadFactors = spreadFactors;
	}

	public Date getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public ChannelConfiguration getChannelConfiguration() {
		return this.channelConfiguration;
	}

	public void setChannelConfiguration(ChannelConfiguration channelConfiguration) {
		this.channelConfiguration = channelConfiguration;
	}

}