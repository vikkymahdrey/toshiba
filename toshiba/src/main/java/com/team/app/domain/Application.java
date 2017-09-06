package com.team.app.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the application database table.
 * 
 */
@Entity
@NamedQuery(name="Application.findAll", query="SELECT a FROM Application a")
public class Application implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String id;

	@Column(name="adr_interval")
	private String adrInterval;

	private String description;

	@Column(name="installation_margin")
	private String installationMargin;

	@Column(name="is_abp")
	private String isAbp;

	@Column(name="is_class_c")
	private String isClassC;

	private String name;

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

	//bi-directional many-to-one association to Organization
	@ManyToOne(fetch=FetchType.LAZY)
	private Organization organization;

	//bi-directional many-to-one association to ApplicationUser
	@OneToMany(mappedBy="application")
	private List<ApplicationUser> applicationUsers;

	//bi-directional many-to-one association to Node
	@OneToMany(mappedBy="application")
	private List<Node> nodes;

	public Application() {
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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Organization getOrganization() {
		return this.organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public List<ApplicationUser> getApplicationUsers() {
		return this.applicationUsers;
	}

	public void setApplicationUsers(List<ApplicationUser> applicationUsers) {
		this.applicationUsers = applicationUsers;
	}

	public ApplicationUser addApplicationUser(ApplicationUser applicationUser) {
		getApplicationUsers().add(applicationUser);
		applicationUser.setApplication(this);

		return applicationUser;
	}

	public ApplicationUser removeApplicationUser(ApplicationUser applicationUser) {
		getApplicationUsers().remove(applicationUser);
		applicationUser.setApplication(null);

		return applicationUser;
	}

	public List<Node> getNodes() {
		return this.nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

	public Node addNode(Node node) {
		getNodes().add(node);
		node.setApplication(this);

		return node;
	}

	public Node removeNode(Node node) {
		getNodes().remove(node);
		node.setApplication(null);

		return node;
	}

}