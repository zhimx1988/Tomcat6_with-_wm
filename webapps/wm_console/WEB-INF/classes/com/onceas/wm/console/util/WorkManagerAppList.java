package com.onceas.wm.console.util;

import java.util.ArrayList;

public class WorkManagerAppList {
	public static int MAX = 100;

	public static ArrayList<WorkManagerAppStaNode> appList = new ArrayList<WorkManagerAppStaNode>(MAX);

	public static void add(WorkManagerNode node) {

		for (int i = 0; i < appList.size(); i++) {
			WorkManagerAppStaNode temp = (WorkManagerAppStaNode) appList.get(i);
			if (temp.getAppName().equals(node.getApplicationName())) {
				temp.add(node);
				return;
			}
		}
		WorkManagerAppStaNode newAppNode = new WorkManagerAppStaNode();
		newAppNode.add(node);
		newAppNode.setAppName(node.getApplicationName()) ;
		appList.add(newAppNode);

	}

	 public static void add(WorkManagerNode node,int x){
		 for(WorkManagerAppStaNode staNode: appList){
			 if(node.getApplicationName().endsWith(staNode.getAppName())){
				 //find
				 staNode.add(node);
				 return;
			 }
			 
		 }
		 //coming here means not find ,so create
		 WorkManagerAppStaNode  staNode = new WorkManagerAppStaNode();
		 staNode.add(node);
		 staNode.setAppName(node.getApplicationName()) ;
		 appList.add(staNode);
	 }
	
	public static WorkManagerAppStaNode get(String name) {
		for (int i = 0; i < appList.size(); i++) {
			WorkManagerAppStaNode temp = (WorkManagerAppStaNode) appList.get(i);
			if (temp.getAppName().equals(name)) {
				return temp;
			}
		}
		return null;
	}

}
