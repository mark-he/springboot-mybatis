package com.eagletsoft.boot.auth.core.filter;

import com.eagletsoft.boot.framework.common.session.UserInterface;
import com.eagletsoft.boot.framework.common.session.UserSession;

import javax.servlet.*;
import java.io.IOException;

public class AnonymousFilter implements Filter {
    private String username;
    private String userId;
    @Override
    public void init(FilterConfig filterChain) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        UserSession.setAuthorize(new UserSession.Authorize<>(new UserInterface() {
            @Override
            public Object getId() {
                return userId;
            }

            @Override
            public Object getName() {
                return username;
            }
        }));

        filterChain.doFilter(request, response);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
