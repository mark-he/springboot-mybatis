package com.eagletsoft.boot.profile.core.organization.model;


import com.eagletsoft.boot.framework.data.entity.UuidAuditableEntity;

import javax.validation.constraints.NotBlank;

public class Position extends UuidAuditableEntity {
	private String parentId;
	@NotBlank
	private String name;
	private String description;
	private String state;

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public interface States {
		String ENABLED = "ENABLED";
		String DISABLED = "DISABLED";
	}
}
