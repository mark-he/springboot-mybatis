package com.eagletsoft.boot.framework.api.utils;

import com.eagletsoft.boot.framework.common.errors.ServiceException;
import com.eagletsoft.boot.framework.common.errors.StandardErrors;
import com.eagletsoft.boot.framework.common.i18n.MessageMaker;
import com.eagletsoft.boot.framework.common.utils.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.Writer;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class ApiExceptionResolver implements HandlerExceptionResolver, Ordered {

	@Autowired
	private MessageMaker messageMaker;

	private static final Logger LOG = getLogger(ApiExceptionResolver.class);

	
    public int getOrder() {
        return HIGHEST_PRECEDENCE; 
    }
	
	@PostConstruct
	private void init()
	{
	}
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(HttpServletResponse.SC_OK);
		ApiResponse result = null;

		Throwable cause = findCause(ex, ConstraintViolationException.class);
		if (null != cause) {
			ConstraintViolationException cve = (ConstraintViolationException)cause;
			StringBuffer sb = new StringBuffer();
			for (ConstraintViolation cv : cve.getConstraintViolations()) {
				if (sb.length() > 0) {
					sb.append(", ");
				}
				String message = cv.getMessage();
				if (message.startsWith("{")) {
					int idx = message.indexOf("}");
					message = message.substring(1, idx);
					message = messageMaker.make(message, null, null);
				}
	            sb.append(String.format("%s: %s", cv.getPropertyPath(), message));
			}
			result = ApiResponse.make(StandardErrors.VALIDATION.getStatus(), sb.toString(), null);
			
		} else if (ex instanceof org.springframework.dao.DataIntegrityViolationException) {
			cause = findCause(ex, "com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException");
	        result = ApiResponse.make(StandardErrors.INTERNAL_ERROR.getStatus(), messageMaker.make("error.framework.db.update"), null);
			if (null != cause) {
				if (JsonUtils.writeValue(cause).contains("foreign key constraint")) {
					result = ApiResponse.make(StandardErrors.INTERNAL_ERROR.getStatus(), messageMaker.make("error.framework.db.remove"), null);
				}
			}
		} else if (ex instanceof MethodArgumentNotValidException){
			BindingResult errorResult = ((MethodArgumentNotValidException)ex).getBindingResult();
	        List<FieldError> errors = errorResult.getFieldErrors();
	        StringBuffer message = new StringBuffer();
	        for(FieldError error : errors) {
	        	String field = error.getField();
	            String code = error.getDefaultMessage();
	            if (message.length() > 0) {
	            	message.append(", ");
	            }
	            message.append(String.format("%s: %s", field, code));
	        }
	        result = ApiResponse.make(StandardErrors.VALIDATION.getStatus(), message.toString(), null);
		}
		else if (ex instanceof ServiceException)
		{
			ServiceException serviceEx = (ServiceException)ex;
			result = ApiResponse.make(serviceEx.getStatus(), messageMaker.make(serviceEx.getKey(), serviceEx.getParams()), null);

		}
		else if (ex instanceof org.springframework.http.converter.HttpMessageNotReadableException)
		{
			result = ApiResponse.make(StandardErrors.CLIENT_ERROR.getStatus(), messageMaker.make("error.framework.request.data"), null);
		}
		else {
			result = ApiResponse.make(StandardErrors.UNKNOWN.getStatus(),  messageMaker.make(StandardErrors.UNKNOWN.getMessage()));
		}
		
		Writer writer = null;
		try
		{
			ObjectMapper om = JsonUtils.createMapper();
			String value = om.writeValueAsString(result);
			writer = response.getWriter();
			writer.write(value);
			writer.flush();
		}
		catch (Exception ex2)
		{
			LOG.error("Error when output information to client", ex2);
		}
		finally
		{
			IOUtils.closeQuietly(writer);
		}
		return new ModelAndView();
	}
	
	private Throwable findCause(Throwable ex, Class clazz) {
		if (ex.getClass().equals(clazz)) {
			return ex;
		}
		if (null == ex.getCause()) {
			return null;
		}
		else {
			if (ex.getCause().getClass().equals(clazz)) {
				return ex.getCause();
			}
			else {
				return findCause(ex.getCause(), clazz);
			}
		}
	}

	private Throwable findCause(Throwable ex, String clazzName) {
		if (ex.getClass().getName().equals(clazzName)) {
			return ex;
		}
		if (null == ex.getCause()) {
			return null;
		}
		else {
			if (ex.getCause().getClass().getName().equals(clazzName)) {
				return ex.getCause();
			}
			else {
				return findCause(ex.getCause(), clazzName);
			}
		}
	}
}
