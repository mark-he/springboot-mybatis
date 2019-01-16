package com.eagletsoft.boot.profile.core.organization.model;


import com.eagletsoft.boot.framework.data.entity.UuidAuditableEntity;

public class PositionPermission extends UuidAuditableEntity {

	private String positionId;
	private String permissionId;
	public String getPositionId() {
		return positionId;
	}
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}
	public String getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}
	
}
