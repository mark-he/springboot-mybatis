package com.eagletsoft.boot.profile.api.v1.organization;

import com.eagletsoft.boot.framework.api.BaseApi;
import com.eagletsoft.boot.framework.common.security.meta.Access;
import com.eagletsoft.boot.framework.data.service.BaseService;
import com.eagletsoft.boot.profile.core.organization.UserService;
import com.eagletsoft.boot.profile.core.organization.dto.UpdateUserRequest;
import com.eagletsoft.boot.profile.core.organization.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/v1/organization/user")
@Access("organization.user")
public class UserApi extends BaseApi<User, UpdateUserRequest> {

    @Autowired private UserService service;

    @Override
    protected BaseService<User> getService() {
        return service;
    }



}
