package com.eagletsoft.boot.profile.core.auth;

import com.eagletsoft.boot.auth.core.AccessTokenService;
import com.eagletsoft.boot.auth.core.dto.GrantTokenRequest;
import com.eagletsoft.boot.auth.core.errors.ErrorMessages;
import com.eagletsoft.boot.auth.core.handlers.BaseAuthHandler;
import com.eagletsoft.boot.auth.core.model.AccessToken;
import com.eagletsoft.boot.auth.core.model.Client;
import com.eagletsoft.boot.framework.common.errors.ServiceException;
import com.eagletsoft.boot.framework.common.errors.StandardErrors;
import com.eagletsoft.boot.framework.common.session.UserSession;
import com.eagletsoft.boot.profile.core.organization.UserService;
import com.eagletsoft.boot.profile.core.organization.model.User;
import org.springframework.beans.factory.annotation.Autowired;


public class ProfileAuthHandler extends BaseAuthHandler {

	@Autowired
	private UserService userService;
	@Autowired
	private AccessTokenService accessTokenService;
	@Override
	protected AccessToken createToken(Client client, GrantTokenRequest req) {
		User user = userService.findByLoginName(req.getUsername());
		if (null == user) {
			throw new ServiceException(StandardErrors.AUTHORIZED_WRONG.getStatus(), ErrorMessages.AUTH_USER_NAMENOTEXISTS);
		}
		
		if (!User.States.ENABLED.equals(user.getState())) {
			throw new ServiceException(StandardErrors.AUTHORIZED_WRONG.getStatus(), ErrorMessages.AUTH_USER_DISABLED);
		}
		
		boolean ret = userService.validateWithCode(user.getId(), req.getAuthorizedCode());
		if (!ret) {
			throw new ServiceException(StandardErrors.AUTHORIZED_WRONG.getStatus(), ErrorMessages.AUTH_USER_CODE_WRONG);
		}
		return accessTokenService.createForClientUser(client, req.getScope(), user.getId());
	}

	@Override
	protected UserSession.Authorize<?> authorize(Client client, AccessToken accessToken) {
		User user = userService.find(accessToken.getUserId());
		if (null == user) {
			throw new ServiceException(StandardErrors.UNAUTHORIZED.getStatus(), ErrorMessages.AUTH_USER_NOTEXISTS);
		}
		if (!User.States.ENABLED.equals(user.getState())) {
			throw new ServiceException(StandardErrors.UNAUTHORIZED.getStatus(), ErrorMessages.AUTH_USER_DISABLED);
		}
		
		UserSession.Authorize auth = userService.findAuthorize(user, accessToken.getToken());
		return auth;
	}
}
