package com.eagletsoft.boot.framework.api;

import com.eagletsoft.boot.framework.api.utils.ApiResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/")
public class DefaultApi {
    @RequestMapping(value = "/",method = {RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody Object index(){
        return ApiResponse.make(0,"Server is Working");
    }
}
