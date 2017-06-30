package com.onceas.wm.console.util;

public class WorkManagerNode {

	private int stuckThreadCount;

	private String moduleName;

	private int pendingRequests;

	private long completedRequests;

	private String name;

	private String type;

	private String applicationName;

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public long getCompletedRequests() {
		return completedRequests;
	}

	public void setCompletedRequests(long completedRequests) {
		this.completedRequests = completedRequests;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPendingRequests() {
		return pendingRequests;
	}

	public void setPendingRequests(int pendingRequests) {
		this.pendingRequests = pendingRequests;
	}

	public int getStuckThreadCount() {
		return stuckThreadCount;
	}

	public void setStuckThreadCount(int stuckThreadCount) {
		this.stuckThreadCount = stuckThreadCount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


}
