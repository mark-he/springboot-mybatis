package com.eagletsoft.boot.profile.core.organization.model;

import com.eagletsoft.boot.framework.data.entity.UuidAuditableEntity;

import java.util.Date;

public class UserAuth extends UuidAuditableEntity {
	private String userId;
	private String password;
	private Date lastLoginTime;
	private int fails;

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public int getFails() {
		return fails;
	}
	public void setFails(int fails) {
		this.fails = fails;
	}

}
