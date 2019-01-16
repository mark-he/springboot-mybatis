package com.eagletsoft.boot.profile.core.organization.model;

import com.eagletsoft.boot.framework.data.entity.UuidAuditableEntity;

import javax.validation.constraints.NotBlank;

public class Organization extends UuidAuditableEntity {
	private String parentId;

	@NotBlank
	private String code;

	@NotBlank
	private String name;

	private String address;

	private boolean enabled;

	private String description;

	@NotBlank
	private String type;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
