package com.onceas.wm.console.model;

import java.util.Collection;

/**
 * 存储一个应用关联的所有WorkManagers
 * @author syk
 */
public class WorkManagerSnapshot {

	private String appName;
	
	private Collection<WorkManagerConfigBean> wmConfigBeans;
	

	public WorkManagerSnapshot(String appName, Collection<WorkManagerConfigBean> wmConfigBeans) {
		this.appName = appName;
		this.wmConfigBeans = wmConfigBeans;
	}

	public String getAppName() {
		return appName;
	}

	public Collection<WorkManagerConfigBean> getWmConfigBeans() {
		return wmConfigBeans;
	}
	
	public int getSize(){
		return wmConfigBeans.size();
	}
}
