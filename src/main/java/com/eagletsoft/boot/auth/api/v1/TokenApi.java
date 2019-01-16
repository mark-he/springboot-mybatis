package com.eagletsoft.boot.auth.api.v1;

import com.eagletsoft.boot.auth.core.AccessTokenService;
import com.eagletsoft.boot.auth.core.dto.GrantTokenRequest;
import com.eagletsoft.boot.auth.core.dto.GrantTokenResponse;
import com.eagletsoft.boot.auth.core.dto.ValidateTokenRequest;
import com.eagletsoft.boot.framework.api.utils.ApiResponse;
import com.eagletsoft.boot.framework.common.session.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "public/v1/auth")
public class TokenApi {
	@Autowired
	private AccessTokenService accessTokenService;

	@RequestMapping(value = "grant", method = {RequestMethod.POST})
	public @ResponseBody Object grant(@RequestBody @Valid GrantTokenRequest req) {
		GrantTokenResponse result = accessTokenService.grant(req);
		return ApiResponse.make(result);
	}

	@RequestMapping(value = "validate", method = {RequestMethod.POST})
	public @ResponseBody Object validate(@RequestBody @Valid ValidateTokenRequest req) {
		UserSession.Authorize<?> auth = accessTokenService.validate(req);
		return ApiResponse.make(auth);
	}
}
