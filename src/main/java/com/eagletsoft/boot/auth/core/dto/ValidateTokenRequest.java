package com.eagletsoft.boot.auth.core.dto;


import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class ValidateTokenRequest implements Serializable {
	@NotBlank
	private String token;
	private String scope;
	private String resource;
	
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
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
}
