package com.team.app.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the gorp_migrations_ns database table.
 * 
 */
@Entity
@Table(name="gorp_migrations_ns")
@NamedQuery(name="GorpMigrationsN.findAll", query="SELECT g FROM GorpMigrationsN g")
public class GorpMigrationsN implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="applied_at")
	private Date appliedAt;

	private String ids;

	public GorpMigrationsN() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getAppliedAt() {
		return this.appliedAt;
	}

	public void setAppliedAt(Date appliedAt) {
		this.appliedAt = appliedAt;
	}

	public String getIds() {
		return this.ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

}