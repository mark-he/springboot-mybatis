package com.eagletsoft.boot.auth.core.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eagletsoft.boot.framework.data.entity.UuidAuditableEntity;

import java.beans.Transient;

@TableName("access_token")
public class AccessToken extends UuidAuditableEntity {

	private String userId;
	private String clientId;
	private String token;
	private String type;
	private long validity;
	private String scope;

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public long getValidity() {
		return validity;
	}

	public void setValidity(long validity) {
		this.validity = validity;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	@Transient
	public boolean isExpired() {
		long now = System.currentTimeMillis();
		if(updatedTime == null) {
			return true;
		}
		if(-1 == this.validity) {
			return false;
		}
		long time = updatedTime.getTime() + (validity * 1000);
		return now >= time;
	}

	public interface Types {
		String BEARER = "BEARER";
	}
}
