package com.eagletsoft.boot.auth.core.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eagletsoft.boot.framework.data.entity.UuidAuditableEntity;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;

@TableName("client")
public class Client extends UuidAuditableEntity {

	@NotBlank
	private String name;
	@NotBlank
	private String secret;

	private String scope;
	
	private String resources;
	
	@NotBlank
	private String state = States.ENABLED;
	
	@NotBlank
	private String grantType = GrantTypes.PASSWORD;
	
	private long validity;
	
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getResources() {
		return resources;
	}
	public void setResources(String resources) {
		this.resources = resources;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getGrantType() {
		return grantType;
	}
	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getValidity() {
		return validity;
	}
	public void setValidity(long validity) {
		this.validity = validity;
	}
	
	public boolean containsGrantType(String grantType) {
		if(StringUtils.isEmpty(grantType)) {
			return true;
		}
		if(StringUtils.isEmpty(this.grantType)) {
			return false;
		}
		String[] grantTypeArray = this.grantType.split(",");
		for(String grantTypeValue:grantTypeArray) {
			if(grantTypeValue.trim().equals(grantType)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean containsResource(String resource) {
		if(StringUtils.isEmpty(resource)) {
			return true;
		}
		if(StringUtils.isEmpty(this.resources)) {
			return false;
		}
		String[] resourceArray = this.resources.split(",", -1);
		for(String resourceValue : resourceArray) {
			if(resourceValue.trim().equals(resource)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean containsScope(String scope) {
		if(StringUtils.isEmpty(scope)) {
			return true;
		}
		if(StringUtils.isEmpty(this.scope)) {
			return false;
		}
		String[] scopeArray = this.scope.split(",", -1);
		for(String scopeValue:scopeArray) {
			if(scopeValue.trim().equals(scope)) {
				return true;
			}
		}
		return false;
	}

	public interface GrantTypes {
		String PASSWORD = "PASSWORD";
		String CLIENT_CREDEDNTIAL = "CLIENT_CREDENTIAL";
	}

	public interface States {
		String ENABLED = "ENABLED";
		String DISABLED = "DISABLED";
	}
}
