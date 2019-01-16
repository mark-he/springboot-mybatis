package com.eagletsoft.boot.auth.core.filter;

import com.eagletsoft.boot.auth.core.AccessTokenService;
import com.eagletsoft.boot.auth.core.dto.ValidateTokenRequest;
import com.eagletsoft.boot.framework.api.utils.ApiResponse;
import com.eagletsoft.boot.framework.common.errors.StandardErrors;
import com.eagletsoft.boot.framework.common.session.UserSession;
import com.eagletsoft.boot.framework.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AuthFilter implements Filter {

    private String resourceId;
    private String scope;
    @Autowired
    private AccessTokenService accessTokenService;

    @Override
    public void init(FilterConfig filterChain) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        final String accessToken = httpRequest.getParameter("access_token");
        try {
            UserSession.setAuthorize(null);
            ValidateTokenRequest validateTokenRequest = new ValidateTokenRequest();
            validateTokenRequest.setResource(resourceId);
            validateTokenRequest.setScope(scope);
            validateTokenRequest.setToken(accessToken);

            UserSession.Authorize<?> auth = accessTokenService.validate(validateTokenRequest);

            if (null == auth) {
                ApiResponse resp = ApiResponse.make(StandardErrors.UNAUTHORIZED.getStatus(), "User not found or was disabled.", null);
                response.getWriter().write(JsonUtils.writeValue(resp));
                response.getWriter().flush();
            }
            else {
                UserSession.setAuthorize(auth);
                filterChain.doFilter(request, response);
            }
        }
        catch (Exception ex) {
            ApiResponse resp = ApiResponse.make(StandardErrors.UNAUTHORIZED.getStatus(), "User is not authorized to specified resource", null);
            response.getWriter().write(JsonUtils.writeValue(resp));
            response.getWriter().flush();
        }
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
