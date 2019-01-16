package com.eagletsoft.boot.projectname.api.v1;

import com.eagletsoft.boot.framework.api.utils.ApiResponse;
import com.eagletsoft.boot.framework.data.query.Filter;
import com.eagletsoft.boot.framework.data.query.PageRequest;
import com.eagletsoft.boot.profile.core.organization.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/test")
public class TestApi {
    @Autowired
    UserService userService;

    @RequestMapping(value = "user", method = {RequestMethod.POST})
    public @ResponseBody
    Object user() {
        Object user = userService.query(new PageRequest().addFilter(new Filter("id", "=", "402880f2646e85f001646ed26987001f")));
        return ApiResponse.make(user);
    }
}
