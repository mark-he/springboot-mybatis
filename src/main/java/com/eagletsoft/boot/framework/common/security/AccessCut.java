package com.eagletsoft.boot.framework.common.security;

import com.eagletsoft.boot.framework.common.errors.ServiceException;
import com.eagletsoft.boot.framework.common.errors.StandardErrors;
import com.eagletsoft.boot.framework.common.security.meta.Access;
import com.eagletsoft.boot.framework.common.session.UserSession;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;
import java.util.List;

@Aspect
public class AccessCut {
	private @Autowired(required = false) Interceptor interceptor;
	private static ThreadLocal<List<String>> PERMSTACK = new ThreadLocal<>();
	
	@Around(value = "@annotation(access)") 
	public Object around(ProceedingJoinPoint point, Access access) throws Throwable {
		if (access.disabled()) {
			throw new ServiceException(StandardErrors.UNAUTHORIZED.getStatus(), "Disabled Function");
		}
		Access classAccess = point.getTarget().getClass().getAnnotation(Access.class);
		String value = null;
		if (null != classAccess) {
			String p = null;
			
			String[] clsAccessArr = classAccess.value().split(",", -1);
			for (int i = 0; i < clsAccessArr.length; i++) {
				value = clsAccessArr[i].trim() + access.value();
				p = UserSession.findPermission(value);
				if (null != p) {
					break;
				}
			}
			
			if (null == p)
			{
				throw new ServiceException(StandardErrors.UNAUTHORIZED.getStatus(), "No permission: " + value);
			}
		}
		if (null != value) {
			push(value);
		}
		Object ret = point.proceed();
		if (null != value) {
			pop();
		}
		if (null != interceptor) {
			interceptor.intercept(ret);
		}
		return ret;
	}

	private static void push(String p) {
		List<String> ps = PERMSTACK.get();
		if (null == ps) {
			ps = new LinkedList<String>();
			PERMSTACK.set(ps);
		}
		ps.add(p);
	}
	
	private static void pop() {
		List<String> ps = PERMSTACK.get();
		if (null != ps) {
			if (ps.size() > 0) {
				ps.remove(ps.size() - 1);
			}
		}
	}
	
	public String top() {
		List<String> ps = PERMSTACK.get();
		if (null != ps) {
			return ps.get(ps.size() - 1);
		}
		return null;
	}
	
	public static interface Interceptor {
		void intercept(Object proceedResult);
	}
}