package com.eagletsoft.boot.framework.common.i18n;

import com.eagletsoft.boot.framework.common.errors.ServiceException;
import com.eagletsoft.boot.framework.common.session.UserSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;


@Component
public class MessageMaker {
	@Autowired
	private MessageSource messageSource;
	
	private static final Logger LOG = LoggerFactory.getLogger(MessageMaker.class);

	
	public String make(String key)
	{
		try {
			return messageSource.getMessage(key, null, UserSession.getLocale());
		}
		catch (Exception ex) {
			return key;
		}
	}

	public String make(String key, Object... params)
	{
		try {
			return messageSource.getMessage(key, params, UserSession.getLocale());
		}
		catch (Exception ex) {
			return key;
		}
	}
	
	public String make(Exception ex)
	{
		return make(null, ex);
	}
	
	public String make(String namespace, Throwable ex)
	{
		String key = null;
	    if (ex instanceof ServiceException)
		{
	    	if (null != ex.getCause())
	    	{
	    		LOG.error("Inner Error", ex);
	    	}
	    	key = ((ServiceException)ex).getKey();
			return getNamespaceMessage(namespace, key, ((ServiceException)ex).getParams());
		}
		return ex.getMessage();
	}
	
	private String getNamespaceMessage(String namespace, String key, Object[] params)
	{
		String[] nsParams = null;
		if (null != namespace && namespace.length() > 0)
		{
			String nsParamKey = namespace + "@" + key;
			String nsKeyValue = messageSource.getMessage(nsParamKey, params, UserSession.getLocale());//return the key if null
			return nsKeyValue;
		}
		else
		{
			return messageSource.getMessage(key, nsParams, UserSession.getLocale());
		}
	}
}
