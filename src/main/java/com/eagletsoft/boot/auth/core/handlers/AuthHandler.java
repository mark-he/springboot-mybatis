package com.eagletsoft.boot.auth.core.handlers;

import com.eagletsoft.boot.auth.core.dto.GrantTokenRequest;
import com.eagletsoft.boot.auth.core.dto.GrantTokenResponse;
import com.eagletsoft.boot.auth.core.dto.ValidateTokenRequest;
import com.eagletsoft.boot.framework.common.session.UserSession;

public interface AuthHandler {
	GrantTokenResponse grant(GrantTokenRequest req);
	UserSession.Authorize<?> validate(ValidateTokenRequest req);
}
