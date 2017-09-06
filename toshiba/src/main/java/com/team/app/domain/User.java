package com.team.app.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	private Date createdAt;

	@Column(name="is_active")
	private String isActive;

	@Column(name="is_admin")
	private String isAdmin;

	@Column(name="password_hash")
	private String passwordHash;

	@Column(name="session_ttl")
	private String sessionTtl;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_at")
	private Date updatedAt;

	private String username;

	//bi-directional many-to-one association to ApplicationUser
	@OneToMany(mappedBy="user")
	private List<ApplicationUser> applicationUsers;

	//bi-directional many-to-one association to OrganizationUser
	@OneToMany(mappedBy="user")
	private List<OrganizationUser> organizationUsers;

	public User() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getIsAdmin() {
		return this.isAdmin;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getPasswordHash() {
		return this.passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getSessionTtl() {
		return this.sessionTtl;
	}

	public void setSessionTtl(String sessionTtl) {
		this.sessionTtl = sessionTtl;
	}

	public Date getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<ApplicationUser> getApplicationUsers() {
		return this.applicationUsers;
	}

	public void setApplicationUsers(List<ApplicationUser> applicationUsers) {
		this.applicationUsers = applicationUsers;
	}

	public ApplicationUser addApplicationUser(ApplicationUser applicationUser) {
		getApplicationUsers().add(applicationUser);
		applicationUser.setUser(this);

		return applicationUser;
	}

	public ApplicationUser removeApplicationUser(ApplicationUser applicationUser) {
		getApplicationUsers().remove(applicationUser);
		applicationUser.setUser(null);

		return applicationUser;
	}

	public List<OrganizationUser> getOrganizationUsers() {
		return this.organizationUsers;
	}

	public void setOrganizationUsers(List<OrganizationUser> organizationUsers) {
		this.organizationUsers = organizationUsers;
	}

	public OrganizationUser addOrganizationUser(OrganizationUser organizationUser) {
		getOrganizationUsers().add(organizationUser);
		organizationUser.setUser(this);

		return organizationUser;
	}

	public OrganizationUser removeOrganizationUser(OrganizationUser organizationUser) {
		getOrganizationUsers().remove(organizationUser);
		organizationUser.setUser(null);

		return organizationUser;
	}

}