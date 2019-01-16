package com.eagletsoft.boot.projectname.api;

import com.eagletsoft.boot.framework.common.session.UserSession;
import com.eagletsoft.boot.projectname.CoreFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ApiServlet extends DispatcherServlet {

	private static Logger LOG = LoggerFactory.getLogger(ApiServlet.class);	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	@Override
	protected void doDispatch(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try
		{
			CoreFramework.getInstance().start();
			UserSession.setLocale(request.getLocale());
			super.doDispatch(request, response);
		}
		catch(Exception ex)
		{
			CoreFramework.getInstance().rollback();
		}
		finally {
			CoreFramework.getInstance().stop();
		}
	}
}
