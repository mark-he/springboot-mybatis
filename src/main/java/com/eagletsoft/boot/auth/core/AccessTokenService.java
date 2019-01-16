package com.eagletsoft.boot.auth.core;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eagletsoft.boot.auth.core.dao.AccessTokenDao;
import com.eagletsoft.boot.auth.core.dto.GrantTokenRequest;
import com.eagletsoft.boot.auth.core.dto.GrantTokenResponse;
import com.eagletsoft.boot.auth.core.dto.ValidateTokenRequest;
import com.eagletsoft.boot.auth.core.errors.ErrorMessages;
import com.eagletsoft.boot.auth.core.handlers.AuthHandler;
import com.eagletsoft.boot.auth.core.model.AccessToken;
import com.eagletsoft.boot.auth.core.model.Client;
import com.eagletsoft.boot.framework.common.errors.ServiceException;
import com.eagletsoft.boot.framework.common.errors.StandardErrors;
import com.eagletsoft.boot.framework.common.session.UserSession;
import com.eagletsoft.boot.framework.common.utils.UuidUtils;
import com.eagletsoft.boot.framework.data.service.BaseService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;


@Service
public class AccessTokenService extends BaseService<AccessToken> {

	@Resource
	private AccessTokenDao inner;

	@Override
	protected BaseMapper<AccessToken> getInner() {
		return inner;
	}

	@Resource(name = "authClientHandlers")
	private Map<String, AuthHandler> handlerRegister;

	@Value("${auth.token.create.always}")
	private boolean CREATE_ALWAYS = true;

	private AuthHandler findHandlerForClient(String clientId) {
		AuthHandler handler = handlerRegister.get(clientId);
		if (null == handler) {
			handler = handlerRegister.get("default");
		}
		return handler;
	}

	public GrantTokenResponse grant(GrantTokenRequest req) {
		AuthHandler handler = this.findHandlerForClient(req.getClientId());
		return handler.grant(req);
	}

	public UserSession.Authorize<?> validate(ValidateTokenRequest req) {
		AccessToken accessToken = this.validateToken(req.getToken());
		AuthHandler handler = this.findHandlerForClient(accessToken.getClientId());
		return handler.validate(req);
	}

	public AccessToken validateToken(String token) {
		AccessToken accessToken = this.inner.findByToken(token);
		if(accessToken == null) {
			throw new ServiceException(StandardErrors.UNAUTHORIZED.getStatus(), ErrorMessages.AUTH_TOKEN_NOTEXISTS);
		}
		if(accessToken.isExpired()) {
			throw new ServiceException(StandardErrors.UNAUTHORIZED.getStatus(), ErrorMessages.AUTH_TOKEN_EXPIRED);
		}
		return accessToken;
	}

	public AccessToken createForClientUser(Client client, String scope, String userId) {
		AccessToken token = inner.findByClientUser(client.getId(), userId);
		if (null == token) {
			token = new AccessToken();
		}
		token.setUserId(userId);
		token.setClientId(client.getId());
		token.setScope(scope);
		token.setValidity(client.getValidity());
		if (this.CREATE_ALWAYS || token.isExpired()) {
			token.setToken(UuidUtils.getUUID());
		}
		token.setType(AccessToken.Types.BEARER);
		return this.createOrUpdate(token);
	}
}
