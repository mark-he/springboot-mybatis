package com.eagletsoft.boot.auth.core.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eagletsoft.boot.framework.data.entity.UuidEntity;

@TableName("verify_code")
public class VerifyCode extends UuidEntity {
	private String code;
	private String userId;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
