package com.team.app.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the gateway_stats database table.
 * 
 */
@Entity
@Table(name="gateway_stats")
@NamedQuery(name="GatewayStat.findAll", query="SELECT g FROM GatewayStat g")
public class GatewayStat implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String id;

	private String interval;

	private String mac;

	@Column(name="rx_packets_received")
	private String rxPacketsReceived;

	@Column(name="rx_packets_received_ok")
	private String rxPacketsReceivedOk;

	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	@Column(name="tx_packets_emitted")
	private String txPacketsEmitted;

	@Column(name="tx_packets_received")
	private String txPacketsReceived;

	public GatewayStat() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInterval() {
		return this.interval;
	}

	public void setInterval(String interval) {
		this.interval = interval;
	}

	public String getMac() {
		return this.mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getRxPacketsReceived() {
		return this.rxPacketsReceived;
	}

	public void setRxPacketsReceived(String rxPacketsReceived) {
		this.rxPacketsReceived = rxPacketsReceived;
	}

	public String getRxPacketsReceivedOk() {
		return this.rxPacketsReceivedOk;
	}

	public void setRxPacketsReceivedOk(String rxPacketsReceivedOk) {
		this.rxPacketsReceivedOk = rxPacketsReceivedOk;
	}

	public Date getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getTxPacketsEmitted() {
		return this.txPacketsEmitted;
	}

	public void setTxPacketsEmitted(String txPacketsEmitted) {
		this.txPacketsEmitted = txPacketsEmitted;
	}

	public String getTxPacketsReceived() {
		return this.txPacketsReceived;
	}

	public void setTxPacketsReceived(String txPacketsReceived) {
		this.txPacketsReceived = txPacketsReceived;
	}

}