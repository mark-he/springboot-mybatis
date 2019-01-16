package com.eagletsoft.boot.profile.core.organization.model;

import com.eagletsoft.boot.framework.common.session.UserInterface;
import com.eagletsoft.boot.framework.data.entity.Auditable;
import com.eagletsoft.boot.framework.data.entity.UuidAuditableEntity;
import com.eagletsoft.boot.framework.data.json.meta.ExtView;
import com.eagletsoft.boot.framework.data.json.meta.LoadOne;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ExtView
public class User extends UuidAuditableEntity implements UserInterface, Auditable {
	private static final long serialVersionUID = -581156393029229615L;

	@NotBlank
	private String loginName;

	@NotBlank
	private String name;

	@NotBlank
	@Email
	private String email;

	private String mobile;

	private String description;

	@NotNull
	private String organizationId;

	private String positionId;

	private String state;
	@LoadOne(target = User.class)
	private String supervisorId;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setName(Object name) {
		this.name = (String)name;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getSupervisorId() {
		return supervisorId;
	}

	public void setSupervisorId(String supervisorId) {
		this.supervisorId = supervisorId;
	}


	public interface States {
		String ENABLED = "ENABLED";
		String DISABLED = "DISABLED";
	}
}
