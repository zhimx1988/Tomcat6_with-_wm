package com.onceas.wm.console.util;

import javax.management.ObjectName;

public class WorkManagerDataFiller extends MBeanServerWrapper{

	public static void fill(){
		ObjectName[] names;
		ObjectName[] childNames;
		names = MServerProxy.queryMBeans("*:type=WorkManagerChild,*");
		childNames = MServerProxy.queryMBeans("*:type=WorkManager,*");
		//WorkManagerNode[] nodes = new WorkManagerNode[names.length];
		WorkManagerAppList.appList.clear();
		for(int i=0;i<names.length;i++){
			WorkManagerNode tempNode = new WorkManagerNode();
			tempNode.setName((String)getAttribute(names[i],"Name")) ;
			tempNode.setApplicationName((String)getAttribute(names[i],"ApplicationName")) ;
			tempNode.setType((String)getAttribute(names[i],"Type"));
			tempNode.setModuleName((String)getAttribute(names[i],"ModuleName"));
			tempNode.setPendingRequests((Integer)getAttribute(names[i],"PendingRequests"));
			tempNode.setStuckThreadCount((Integer)getAttribute(names[i],"StuckThreadCount"));
			tempNode.setCompletedRequests((Long)getAttribute(names[i],"CompletedRequests"));
			WorkManagerAppList.add(tempNode);
		}
		for(int i=0;i<childNames.length;i++){
			WorkManagerNode tempNode = new WorkManagerNode();
			tempNode.setName((String)getAttribute(childNames[i],"Name"));
			tempNode.setApplicationName((String)getAttribute(childNames[i],"ApplicationName"));
			tempNode.setType((String)getAttribute(childNames[i],"Type"));
			tempNode.setModuleName((String)getAttribute(childNames[i],"ModuleName"));
			if(tempNode.getModuleName() == null){
				tempNode.setModuleName("");
			}
			tempNode.setPendingRequests((Integer)getAttribute(childNames[i],"PendingRequests"));
			tempNode.setStuckThreadCount((Integer)getAttribute(childNames[i],"StuckThreadCount"));
			tempNode.setCompletedRequests((Long)getAttribute(childNames[i],"CompletedRequests"));
			WorkManagerAppList.add(tempNode);
		}
	}
}
