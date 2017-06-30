package com.onceas.wm.console.model;

import com.onceas.wm.console.util.ConfigUtil;
import com.onceas.wm.console.util.StringUtils;

/**
 * @author syk
 * 
 */
public class WorkManagerConfigBean {

	private String appName;
	private String wmName;
	private int maxConstraintValue;
	private double cpuValue;
	
	private int maxQueueSize;
	private int inProgress;
	//total completed count
	private long completedCount;
	private long lastCompletedCount;
	
	private String avgUtil;
	
	private String wmType;
	
	/* user name  used only in CONTEXT_CONSTRAINT type*/
	private String user;
	
	/* group name  used only in CONTEXT_CONSTRAINT type*/
	private String group;
	
	private String wmNameReferencedByContextConstraint;

	public WorkManagerConfigBean(String appName, String wmName, int maxContratinValue) {
		this(appName, wmName, maxContratinValue, 0);
	}

	public WorkManagerConfigBean(String appName, String wmName, double cpuValue) {
		this(appName, wmName, 0, cpuValue);
	}
	public WorkManagerConfigBean(String appName, String wmName, int maxContratinValue, double cpuValue) {
		this.appName = appName;
		this.wmName = wmName;
		this.maxConstraintValue = maxContratinValue;
		this.cpuValue = cpuValue;
	}
	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getWmName() {
		return display(wmName);
	}

	public void setWmName(String wmName) {
		this.wmName = wmName;
	}

	public int getMaxConstraintValue() {
		return maxConstraintValue;
	}

	public void setMaxConstraintValue(int maxContratinValue) {
		this.maxConstraintValue = maxContratinValue;
	}

	public double getCpuValue() {
		return cpuValue;
	}

	public void setCpuValue(double cpuValue) {
		this.cpuValue = cpuValue;
	}

	public int getMaxQueueSize() {
		return maxQueueSize;
	}

	public void setMaxQueueSize(int maxQueueSize) {
		this.maxQueueSize = maxQueueSize;
	}

	public int getInProgress() {
		return inProgress;
	}

	public void setInProgress(int inProgress) {
		this.inProgress = inProgress;
	}

	public long getCompletedCount() {
		return completedCount;
	}

	public void setCompletedCount(long completedCount) {
		this.completedCount = completedCount;
	}

	public long getLastCompletedCount() {
		return lastCompletedCount;
	}
	
	public void setLastCompletedCount(long lastCompletedCount) {
		this.lastCompletedCount = lastCompletedCount;
	}

	public double getThroughput(){
		return (double)(completedCount - lastCompletedCount)/(ConfigUtil.getInterval()/1000);
	}

	public String getAvgUtil() {
		return avgUtil;
	}

	public void setAvgUtil(String avgUtil) {
		this.avgUtil = avgUtil;
	}

	public String getWmType() {
		return wmType;
	}

	public void setWmType(String wmType) {
		this.wmType = wmType;
	}

	public String getUser() {
		return display(user);
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getGroup() {
		return display(group);
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getWmNameReferencedByContextConstraint() {
		return display(wmNameReferencedByContextConstraint);
	}

	public void setWmNameReferencedByContextConstraint(String wmNameReferencedByContextConstraint) {
		this.wmNameReferencedByContextConstraint = wmNameReferencedByContextConstraint;
	}
	
	private static String display(String str){
		return StringUtils.displayNullAsNa(str);
	}
}
