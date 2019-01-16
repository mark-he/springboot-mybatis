package com.eagletsoft.boot.auth.core.dto;


import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class GrantTokenRequest implements Serializable {
	@NotBlank
	private String grantType;
	@NotBlank
	private String clientId;
	@NotBlank
	private String secret;
	private String scope;
	private String authorizedCode;
	private String username;
	public String getGrantType() {
		return grantType;
	}
	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAuthorizedCode() {
		return authorizedCode;
	}
	public void setAuthorizedCode(String authorizedCode) {
		this.authorizedCode = authorizedCode;
	}
}
