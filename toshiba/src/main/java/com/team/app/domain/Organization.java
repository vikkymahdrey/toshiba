package com.team.app.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the organization database table.
 * 
 */
@Entity
@NamedQuery(name="Organization.findAll", query="SELECT o FROM Organization o")
public class Organization implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String id;

	@Column(name="can_have_gateway")
	private String canHaveGateway;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	private Date createdAt;

	@Column(name="display_name")
	private String displayName;

	private String name;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_at")
	private Date updatedAt;

	//bi-directional many-to-one association to Application
	@OneToMany(mappedBy="organization")
	private List<Application> applications;

	//bi-directional many-to-one association to Gateway
	@OneToMany(mappedBy="organization")
	private List<Gateway> gateways;

	//bi-directional many-to-one association to OrganizationUser
	@OneToMany(mappedBy="organization")
	private List<OrganizationUser> organizationUsers;

	public Organization() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCanHaveGateway() {
		return this.canHaveGateway;
	}

	public void setCanHaveGateway(String canHaveGateway) {
		this.canHaveGateway = canHaveGateway;
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
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

	public List<Application> getApplications() {
		return this.applications;
	}

	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}

	public Application addApplication(Application application) {
		getApplications().add(application);
		application.setOrganization(this);

		return application;
	}

	public Application removeApplication(Application application) {
		getApplications().remove(application);
		application.setOrganization(null);

		return application;
	}

	public List<Gateway> getGateways() {
		return this.gateways;
	}

	public void setGateways(List<Gateway> gateways) {
		this.gateways = gateways;
	}

	public Gateway addGateway(Gateway gateway) {
		getGateways().add(gateway);
		gateway.setOrganization(this);

		return gateway;
	}

	public Gateway removeGateway(Gateway gateway) {
		getGateways().remove(gateway);
		gateway.setOrganization(null);

		return gateway;
	}

	public List<OrganizationUser> getOrganizationUsers() {
		return this.organizationUsers;
	}

	public void setOrganizationUsers(List<OrganizationUser> organizationUsers) {
		this.organizationUsers = organizationUsers;
	}

	public OrganizationUser addOrganizationUser(OrganizationUser organizationUser) {
		getOrganizationUsers().add(organizationUser);
		organizationUser.setOrganization(this);

		return organizationUser;
	}

	public OrganizationUser removeOrganizationUser(OrganizationUser organizationUser) {
		getOrganizationUsers().remove(organizationUser);
		organizationUser.setOrganization(null);

		return organizationUser;
	}

}