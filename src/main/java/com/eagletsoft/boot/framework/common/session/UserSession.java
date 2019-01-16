package com.eagletsoft.boot.framework.common.session;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;

public class UserSession {
	private static ThreadLocal<Object> authorizeHolder = new ThreadLocal<>();
	private static ThreadLocal<Locale> localeHolder = new ThreadLocal<>();
	
	public static void setLocale(Locale locale) {
		localeHolder.set(locale);
	}
	
	public static Locale getLocale() {
		return localeHolder.get();
	}

	public static <T extends UserInterface> void setAuthorize(String token, T user, Collection<String> permissions) {
		Authorize<T> auth = new Authorize<>(token, user, permissions, Collections.EMPTY_MAP);
		authorizeHolder.set(auth);
	}
	
	public static <T extends UserInterface> void setAuthorize(String token, T user, Collection<String> permissions, Map<String, Object> settings) {
		Authorize<T> auth = new Authorize<>(token, user, permissions, settings);
		authorizeHolder.set(auth);
	}
	
	public static <T extends UserInterface> void setAuthorize(Authorize<T> auth) {
		authorizeHolder.set(auth);
	}
	
	public static <T extends UserInterface> Authorize<T> getAuthorize() {
		Authorize<T> auth = (Authorize<T>)authorizeHolder.get();
		return auth;
	}
	
	public static <T extends UserInterface> T getUserInterface() {
		Authorize<T> auth = (Authorize<T>)authorizeHolder.get();
		if (null == auth) {
			return null;
		}
		return auth.getUser();
	}
	
	
	public static Collection<String> getPermisssions() {
		Authorize<?> auth = (Authorize<?>)authorizeHolder.get();
		Collection<String> permissions = null;
		if (null != auth) {
			permissions = auth.getPermissions();
		}
		if (null != permissions) {
			return permissions;
		}
		else {
			return Collections.EMPTY_SET;
		}
	}
	
	public static String findPermission(String access) {
		String[] accessArr = access.split(",", -1);
		for (int i = 0; i < accessArr.length; i++) {
			String perm = findOnePermission(accessArr[i].trim());
			if (null != perm) {
				return perm;
			}
		}
		return null;
	}

	private static String findOnePermission(String access) {
		Collection<String> permissions = getPermisssions();
		String[] accessSplit = access.split(":", -1);
		
		for (String p : permissions) {
			String[] pSplit = p.split(":", -1);
			if (pSplit[0].equals("*") || pSplit[0].equals(accessSplit[0])) {
				if (pSplit[1].equals("*") || pSplit[1].equals(accessSplit[1])) {
					return p;
				}
			}
		}
		return null;
	}
	
	
	public static void clear() {
		authorizeHolder.remove();
		localeHolder.remove();
	}
	
	public static class Authorize<T extends UserInterface> {
    	private String token;
    	private String clientId;
    	private T user;
    	private Collection<String> permissions;
    	private Map<String, Object> settings;
    	private String scope;
    	
    	public Authorize() {
    		
    	}
    	
    	public Authorize(T user) {
    		this.user = user;
    	}

    	public Authorize(T user, Collection<String> permissions) {
    		this.user = user;
    		this.permissions = permissions;
    	}

    	public Authorize(String token, T user, Collection<String> permissions) {
    		this.token = token;
    		this.user = user;
    		this.permissions = permissions;
    	}
    	
    	public Authorize(String token, T user, Collection<String> permissions, Map<String, Object> settings) {
    		this.token = token;
    		this.user = user;
    		this.permissions = permissions;
    		this.settings = settings;
    	}

    	@JsonIgnore
    	public Object getUserId() {
    		if (null == user) {
    			return null;
    		}
    		else {
    			return user.getId();
    		}
    	}
    	
		public String getToken() {
			return token;
		}
		
		public void setToken(String token) {
			this.token = token;
		}
		
		public T getUser() {
			return user;
		}
		
		public void setUser(T user) {
			this.user = user;
		}
		
		public Collection<String> getPermissions() {
			return permissions;
		}
		
		public void setPermissions(Collection<String> permissions) {
			this.permissions = permissions;
		}

		public Map<String, Object> getSettings() {
			return settings;
		}

		public void setSettings(Map<String, Object> settings) {
			this.settings = settings;
		}

		public String getClientId() {
			return clientId;
		}

		public void setClientId(String clientId) {
			this.clientId = clientId;
		}

		public String getScope() {
			return scope;
		}

		public void setScope(String scope) {
			this.scope = scope;
		}
	}
}
