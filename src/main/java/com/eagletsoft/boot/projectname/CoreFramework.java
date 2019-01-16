package com.eagletsoft.boot.projectname;


import com.eagletsoft.boot.framework.common.session.UserInterface;
import com.eagletsoft.boot.framework.common.session.UserSession;

public class CoreFramework {
	
	private static CoreFramework INSTANCE = new CoreFramework();
	private CoreFramework() {
	}
	
	public static CoreFramework getInstance() {
		return INSTANCE;
	}
	
	public void start() {
		this.cleanup();
	}

	public void dummy() {
		UserSession.setAuthorize(new UserSession.Authorize<>(new UserInterface() {
			@Override
			public Object getId() {
				return "Dummy ID";
			}

			@Override
			public Object getName() {
				return "Dummy Name";
			}
		}));
	}
	
	public void stop() {
		this.cleanup();
	}

	public void rollback() {
		this.cleanup();
	}
	
	private void cleanup() {

	}
}
