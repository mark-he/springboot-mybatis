package com.eagletsoft.boot.auth.core.handlers;

import com.eagletsoft.boot.auth.core.AccessTokenService;
import com.eagletsoft.boot.auth.core.ClientService;
import com.eagletsoft.boot.auth.core.dto.GrantTokenRequest;
import com.eagletsoft.boot.auth.core.dto.GrantTokenResponse;
import com.eagletsoft.boot.auth.core.dto.ValidateTokenRequest;
import com.eagletsoft.boot.auth.core.errors.ErrorMessages;
import com.eagletsoft.boot.auth.core.model.AccessToken;
import com.eagletsoft.boot.auth.core.model.Client;
import com.eagletsoft.boot.framework.common.errors.ServiceException;
import com.eagletsoft.boot.framework.common.errors.StandardErrors;
import com.eagletsoft.boot.framework.common.session.UserSession;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseAuthHandler implements AuthHandler {

	@Autowired
	protected ClientService clientService;
	@Autowired
	protected AccessTokenService tokenService;
	
	protected Client validateClientGrant(String clientId, String secret, String grantType) {
		Client client = clientService.find(clientId);
		if(client == null) {
			throw new ServiceException(StandardErrors.AUTHORIZED_WRONG.getStatus(), ErrorMessages.AUTH_CLIENT_NAMENOTEXISTS);
		}
		
		if(!client.getSecret().equals(secret)) {
			throw new ServiceException(StandardErrors.AUTHORIZED_WRONG.getStatus(), ErrorMessages.AUTH_CLIENT_SECRET_WRONG);
		}
		
		if(!client.containsGrantType(grantType)) {
			throw new ServiceException(StandardErrors.AUTHORIZED_WRONG.getStatus(), ErrorMessages.AUTH_CLIENT_GRANTTYPE_WRONG);
		}
		
		if (!Client.States.ENABLED.equals(client.getState())) {
			throw new ServiceException(StandardErrors.AUTHORIZED_WRONG.getStatus(), ErrorMessages.AUTH_CLIENT_DISABLED);
		}
		
		return client;
	}
	
	protected Client validateClientAccess(AccessToken accessToken, String resource, String scope) {
		Client client = clientService.find(accessToken.getClientId());
		if(!client.containsResource(resource)) {
			throw new ServiceException(StandardErrors.AUTHORIZED_WRONG.getStatus(), ErrorMessages.AUTH_CLIENT_RESOURCE_WRONG);
		}
		
		if(!client.containsScope(scope)) {
			throw new ServiceException(StandardErrors.AUTHORIZED_WRONG.getStatus(), ErrorMessages.AUTH_CLIENT_SCOPE_WRONG);
		}
		
		if (!Client.States.ENABLED.equals(client.getState())) {
			throw new ServiceException(StandardErrors.AUTHORIZED_WRONG.getStatus(), ErrorMessages.AUTH_CLIENT_DISABLED);
		}
		
		return client;
	}
	
	protected AccessToken validateToken(String token) {
		AccessToken accessToken = tokenService.validateToken(token);
		return accessToken;
	}

	protected abstract AccessToken createToken(Client client, GrantTokenRequest req);

	protected abstract UserSession.Authorize<?> authorize(Client client, AccessToken accessToken);

	@Override
	public GrantTokenResponse grant(GrantTokenRequest req) {
		Client client = this.validateClientGrant(req.getClientId(), req.getSecret(), req.getGrantType());
		AccessToken token = createToken(client, req);
		GrantTokenResponse result = new GrantTokenResponse();
		result.setToken(token.getToken());
		long expiry = token.getValidity() - (System.currentTimeMillis() - token.getUpdatedTime().getTime()) / 1000;
		result.setExpriyIn(expiry);
		return result;
	}
	
	@Override
	public UserSession.Authorize<?> validate(ValidateTokenRequest req) {
		AccessToken accessToken = this.validateToken(req.getToken());
		Client client = this.validateClientAccess(accessToken, req.getResource(), req.getScope());
		UserSession.Authorize auth = authorize(client, accessToken);
		auth.setClientId(accessToken.getClientId());
		auth.setScope(accessToken.getScope());
		return auth;
	}
}
