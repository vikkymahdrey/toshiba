package com.team.app.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the channel_configuration database table.
 * 
 */
@Entity
@Table(name="channel_configuration")
@NamedQuery(name="ChannelConfiguration.findAll", query="SELECT c FROM ChannelConfiguration c")
public class ChannelConfiguration implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String id;

	private String band;

	private String channels;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	private Date createdAt;

	private String name;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_at")
	private Date updatedAt;

	//bi-directional many-to-one association to ExtraChannel
	@OneToMany(mappedBy="channelConfiguration")
	private List<ExtraChannel> extraChannels;

	public ChannelConfiguration() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBand() {
		return this.band;
	}

	public void setBand(String band) {
		this.band = band;
	}

	public String getChannels() {
		return this.channels;
	}

	public void setChannels(String channels) {
		this.channels = channels;
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
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

	public List<ExtraChannel> getExtraChannels() {
		return this.extraChannels;
	}

	public void setExtraChannels(List<ExtraChannel> extraChannels) {
		this.extraChannels = extraChannels;
	}

	public ExtraChannel addExtraChannel(ExtraChannel extraChannel) {
		getExtraChannels().add(extraChannel);
		extraChannel.setChannelConfiguration(this);

		return extraChannel;
	}

	public ExtraChannel removeExtraChannel(ExtraChannel extraChannel) {
		getExtraChannels().remove(extraChannel);
		extraChannel.setChannelConfiguration(null);

		return extraChannel;
	}

}