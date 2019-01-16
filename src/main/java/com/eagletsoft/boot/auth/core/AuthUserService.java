package com.eagletsoft.boot.auth.core;

import com.eagletsoft.boot.framework.common.session.UserInterface;
import com.eagletsoft.boot.framework.common.session.UserSession;

public interface AuthUserService {
    UserSession.Authorize<UserInterface> findAuthorize(UserInterface user, String token);
}
