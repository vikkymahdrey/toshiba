package com.team.app.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the node database table.
 * 
 */
@Entity
@NamedQuery(name="Node.findAll", query="SELECT n FROM Node n")
public class Node implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String id;

	@Column(name="adr_interval")
	private String adrInterval;

	@Column(name="app_eui")
	private String appEui;

	@Column(name="app_s_key")
	private String appSKey;

	private String description;

	@Column(name="dev_addr")
	private String devAddr;

	@Column(name="dev_eui")
	private String devEui;

	@Column(name="installation_margin")
	private String installationMargin;

	@Column(name="is_abp")
	private String isAbp;

	@Column(name="is_class_c")
	private String isClassC;

	private String name;

	@Column(name="nwk_s_key")
	private String nwkSKey;

	@Column(name="relax_fcnt")
	private String relaxFcnt;

	@Column(name="rx_delay")
	private String rxDelay;

	@Column(name="rx_window")
	private String rxWindow;

	@Column(name="rx1_dr_offset")
	private String rx1DrOffset;

	@Column(name="rx2_dr")
	private String rx2Dr;

	@Column(name="use_application_settings")
	private String useApplicationSettings;

	@Column(name="used_dev_nonces")
	private String usedDevNonces;

	//bi-directional many-to-one association to Application
	@ManyToOne(fetch=FetchType.LAZY)
	private Application application;

	public Node() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAdrInterval() {
		return this.adrInterval;
	}

	public void setAdrInterval(String adrInterval) {
		this.adrInterval = adrInterval;
	}

	public String getAppEui() {
		return this.appEui;
	}

	public void setAppEui(String appEui) {
		this.appEui = appEui;
	}

	public String getAppSKey() {
		return this.appSKey;
	}

	public void setAppSKey(String appSKey) {
		this.appSKey = appSKey;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDevAddr() {
		return this.devAddr;
	}

	public void setDevAddr(String devAddr) {
		this.devAddr = devAddr;
	}

	public String getDevEui() {
		return this.devEui;
	}

	public void setDevEui(String devEui) {
		this.devEui = devEui;
	}

	public String getInstallationMargin() {
		return this.installationMargin;
	}

	public void setInstallationMargin(String installationMargin) {
		this.installationMargin = installationMargin;
	}

	public String getIsAbp() {
		return this.isAbp;
	}

	public void setIsAbp(String isAbp) {
		this.isAbp = isAbp;
	}

	public String getIsClassC() {
		return this.isClassC;
	}

	public void setIsClassC(String isClassC) {
		this.isClassC = isClassC;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNwkSKey() {
		return this.nwkSKey;
	}

	public void setNwkSKey(String nwkSKey) {
		this.nwkSKey = nwkSKey;
	}

	public String getRelaxFcnt() {
		return this.relaxFcnt;
	}

	public void setRelaxFcnt(String relaxFcnt) {
		this.relaxFcnt = relaxFcnt;
	}

	public String getRxDelay() {
		return this.rxDelay;
	}

	public void setRxDelay(String rxDelay) {
		this.rxDelay = rxDelay;
	}

	public String getRxWindow() {
		return this.rxWindow;
	}

	public void setRxWindow(String rxWindow) {
		this.rxWindow = rxWindow;
	}

	public String getRx1DrOffset() {
		return this.rx1DrOffset;
	}

	public void setRx1DrOffset(String rx1DrOffset) {
		this.rx1DrOffset = rx1DrOffset;
	}

	public String getRx2Dr() {
		return this.rx2Dr;
	}

	public void setRx2Dr(String rx2Dr) {
		this.rx2Dr = rx2Dr;
	}

	public String getUseApplicationSettings() {
		return this.useApplicationSettings;
	}

	public void setUseApplicationSettings(String useApplicationSettings) {
		this.useApplicationSettings = useApplicationSettings;
	}

	public String getUsedDevNonces() {
		return this.usedDevNonces;
	}

	public void setUsedDevNonces(String usedDevNonces) {
		this.usedDevNonces = usedDevNonces;
	}

	public Application getApplication() {
		return this.application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

}