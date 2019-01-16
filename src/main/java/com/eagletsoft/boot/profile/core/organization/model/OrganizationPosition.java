package com.eagletsoft.boot.profile.core.organization.model;

import com.eagletsoft.boot.framework.data.entity.UuidAuditableEntity;

public class OrganizationPosition extends UuidAuditableEntity {

	private String organizationId;
	private String positionId;
	
	public String getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	public String getPositionId() {
		return positionId;
	}
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}
}
