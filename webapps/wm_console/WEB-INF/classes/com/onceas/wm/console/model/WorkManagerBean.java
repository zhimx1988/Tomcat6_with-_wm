package com.onceas.wm.console.model;

/**
 * @author syk
 * 
 */
public class WorkManagerBean {

	private String name;
	private String type;
	
	private String maxThreadConstraintName;
	private int maxThreadConstraintValue;
	
	private String minThreadConstraintName;
	private int minThreadConstraintValue;
	
	private String capacityConstraintName;
	private int capacityConstraintValue;
	
	private String fairShareRequestClassName;
	private int fairShare;
	
	private String responseTimeRequestClassName;
	private int goalTime;
	
	public WorkManagerBean(){
		this.maxThreadConstraintName = "";
		this.minThreadConstraintName = "";
		this.capacityConstraintName = "";
		this.fairShareRequestClassName = "";
		this.responseTimeRequestClassName = "";
	}
	
	public WorkManagerBean(String name, String type) {
		this.name = name;
		this.type = type;
	}
	
	public WorkManagerBean(String name, String maxConstraintName, int maxConstraintValue) {
		this.name = name;
		this.maxThreadConstraintName = maxConstraintName;
		this.maxThreadConstraintValue = maxConstraintValue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

//	public String getMaxConstraintName() {
//		return maxThreadConstraintName;
//	}
//
//	public void setMaxConstraintName(String maxConstraintName) {
//		this.maxThreadConstraintName = maxConstraintName;
//	}

	public String getMaxThreadConstraintName() {
		return maxThreadConstraintName;
	}

	public void setMaxThreadConstraintName(String maxThreadConstraintName) {
		this.maxThreadConstraintName = maxThreadConstraintName;
	}

	public int getMaxThreadConstraintValue() {
		return maxThreadConstraintValue;
	}

	public void setMaxThreadConstraintValue(int maxThreadConstraintValue) {
		this.maxThreadConstraintValue = maxThreadConstraintValue;
	}

	public String getMinThreadConstraintName() {
		return minThreadConstraintName;
	}

	public void setMinThreadConstraintName(String minThreadConstraintName) {
		this.minThreadConstraintName = minThreadConstraintName;
	}

	public int getMinThreadConstraintValue() {
		return minThreadConstraintValue;
	}

	public void setMinThreadConstraintValue(int minThreadConstraintValue) {
		this.minThreadConstraintValue = minThreadConstraintValue;
	}

	public String getCapacityConstraintName() {
		return capacityConstraintName;
	}

	public void setCapacityConstraintName(String capacityConstraintName) {
		this.capacityConstraintName = capacityConstraintName;
	}

	public int getCapacityConstraintValue() {
		return capacityConstraintValue;
	}

	public void setCapacityConstraintValue(int capacityConstraintValue) {
		this.capacityConstraintValue = capacityConstraintValue;
	}

	public String getFairShareRequestClassName() {
		return fairShareRequestClassName;
	}

	public void setFairShareRequestClassName(String fairShareRequestClassName) {
		this.fairShareRequestClassName = fairShareRequestClassName;
	}

	public int getFairShare() {
		return fairShare;
	}

	public void setFairShare(int fairShare) {
		this.fairShare = fairShare;
	}

	public String getResponseTimeRequestClassName() {
		return responseTimeRequestClassName;
	}

	public void setResponseTimeRequestClassName(String responseTimeRequestClassName) {
		this.responseTimeRequestClassName = responseTimeRequestClassName;
	}

	public int getGoalTime() {
		return goalTime;
	}

	public void setGoalTime(int goalTime) {
		this.goalTime = goalTime;
	}

	
//	public int getMaxConstraintValue() {
//		return maxConstraintValue;
//	}
//
//	public void setMaxConstraintValue(int maxConstraintValue) {
//		this.maxConstraintValue = maxConstraintValue;
//	}
	
}
