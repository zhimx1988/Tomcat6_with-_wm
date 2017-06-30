package com.onceas.wm.console.util;

import java.util.ArrayList;
import java.util.List;

public class WorkManagerAppStaNode {
	int MAX = 100;

	private String appName;

	private ArrayList<WorkManagerNode> workManagerNodes = new ArrayList<WorkManagerNode>(MAX);

	public void add(WorkManagerNode node) {
		if (workManagerNodes.size() < MAX) {
			workManagerNodes.add(node);
		}
	}
	
	public String getAppName(){
		return appName;
	}
	
	public void setAppName(String appName) {
		this.appName = appName;
	}

	public List<WorkManagerNode> getWorkManagerNodes(){
		return workManagerNodes;
	}
}
