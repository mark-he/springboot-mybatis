package com.eagletsoft.boot.projectname.api;

import com.eagletsoft.boot.framework.api.utils.ApiExceptionResolver;
import com.eagletsoft.boot.framework.common.errors.ServiceException;
import com.eagletsoft.boot.projectname.CoreFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProjectApiExceptionResolver extends ApiExceptionResolver {
    private static Logger LOG = LoggerFactory.getLogger(ProjectApiExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView ret = super.resolveException(request, response, handler, ex);
        CoreFramework.getInstance().rollback();

        if (ex instanceof ServiceException) {
            LOG.debug("Api 错误", ex);
        }
        else {
            LOG.error("未知错误", ex);
        }
        return ret;
    }
}
