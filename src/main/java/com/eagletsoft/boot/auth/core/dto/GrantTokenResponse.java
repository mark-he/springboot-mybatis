package com.eagletsoft.boot.auth.core.dto;

import java.io.Serializable;

public class GrantTokenResponse implements Serializable {

	private String token;
	private long expriyIn;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

	public long getExpriyIn() {
		return expriyIn;
	}

	public void setExpriyIn(long expriyIn) {
		this.expriyIn = expriyIn;
	}
}
