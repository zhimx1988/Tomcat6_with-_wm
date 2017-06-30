package com.onceas.wm.console.util;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.onceas.runtime.work.Constant;
/**
 * 对webapp的workmanger操作
 * @author Administrator
 *
 */
public class WebAppWorkManagerXmlConfigOp {
	private static Log log = LogFactory.getLog(WebAppWorkManagerXmlConfigOp.class);
	
	final static String WORKMANAGER_CONFIG_BOSS_OBJECT_NAME = Constant.WORK_MANAGER_RUNTIME_MBEAN_DOMAIN + ":"
	+ Constant.RUNTIME_TYPE + "= WorkManagerConfigBoss";
	
	private static ObjectName workManagerConfigBossObjName;
	
//	private String moudleName;
//	
//	public WebAppWorkManagerOp(String moduleName){
//		this.moudleName=moduleName;
//		
//	}
//-------------------------------------------
// workmanger
//------------------------------------------
	public WebAppWorkManagerXmlConfigOp() {
	}
	/**
	 * appName - web application‘s name
	 */
public String[] getWorkManagers(String appName){
	return getWorkManagers(appName,null);
}
	/***
	 *	获得配置的所有wm的名字
	 * @param appName - web application's name
	 * @param moduleName - name of component contained by web application,such as .jsp,servelt 
	 * @return String[0] or workmanagers’ name configed in the web app
	 */
 public String[] getWorkManagers(String appName,String moduleName){											 
	  if(appName==null){
		 return new String[0];
	  }
	  String name;
	  String []workmanagers = null;
	  if(moduleName==null){
		  name = "onceas.work.config:type=WorkManagerConfig,ApplicationName="+appName.trim()+",ModuleName="+moduleName;
	  }else{// wmc child
		  name = "onceas.work.config:type=WorkManagerConfigChild,ApplicationName="+appName.trim()+",ModuleName="+moduleName.trim();
	  }
	  ObjectName objName = null;
	try {
		objName = new ObjectName(name);
	} catch (MalformedObjectNameException e1) {
		e1.printStackTrace();
	} catch (NullPointerException e1) {
		e1.printStackTrace();
	}

	try {
		MBeanServer mserver = MServerProxy.getMBeanServer();
		if(mserver.isRegistered(objName)){
			workmanagers = (String[])(mserver.invoke(objName, "getWorkManagers", new Object[]{}, null));
		}
		
	} catch (InstanceNotFoundException e) {
		e.printStackTrace();
	} catch (MBeanException e) {
		e.printStackTrace();
	} catch (ReflectionException e) {
		e.printStackTrace();
	}
	if(workmanagers == null) return new String[0];
	return workmanagers;
 }
 public String getWorkManagerType(String appName,String wmName){
	return  getWorkManagerType(appName,null,wmName);
 }
 public String getWorkManagerType(String appName,String moduleName,String wmName){
	 return "WorkManager";
 }
 /***
  * 
  * @param appName  web app's name 
  * @param wmName
  */
 public void delWorkManager(String appName,String wmName){
	 delWorkManager(appName, null, wmName);
 }
 
 public void delWorkManager(String appName,String moduleName,String wmName){
	ObjectName objName = constructObjcetName(appName, moduleName);
	if(objName!=null){
		try {
			MServerProxy.getMBeanServer().invoke(objName, "delWorkManager",
												new Object[]{wmName},
												new String[]{"java.lang.String"});
			System.out.println(objName + " has been deleted ");
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		} catch (MBeanException e) {
			e.printStackTrace();
		} catch (ReflectionException e) {
			e.printStackTrace();
		}
	} 
 }

private ObjectName constructObjcetName(String appName, String moduleName) {
	String type;
	ObjectName objName=null;
	
	if(moduleName!=null){
		 type = "WorkManagerConfigChild";
	 }else{
		 type = "WorkManagerConfig";
	 }
	 try {
		 String name = "onceas.work.config:type="+type+",ApplicationName="+trim(appName)+",ModuleName="+trim(moduleName);
		 objName = new ObjectName(name);
		
	} catch (MalformedObjectNameException e) {
		e.printStackTrace();
	} catch (NullPointerException e) {
		e.printStackTrace();
	}
	return objName;
}
 private String trim(String name){
	 if(name == null) return null;
	 return name.trim();
 }
 public void updateWorkManager(String appName,String moduleName,String wmName,String capacityConstraint,String maxThreadsConstraint,String minThreadsConstraint,String requestClass){
	//too many arguments! when needing new constaints or other things, how deal with that?	
	 ObjectName objName = constructObjcetName(appName, moduleName);
		if(objName!=null){
			try {
				MServerProxy.getMBeanServer().invoke(objName, "updateWorkManager",
													new Object[]{new String(appName),new String(moduleName),new String(wmName),
																	new String(capacityConstraint),new String(minThreadsConstraint),new String(requestClass)},
													new String[]{"String","String","String","String","String","String","String"});
			} catch (InstanceNotFoundException e) {
				e.printStackTrace();
			} catch (MBeanException e) {
				e.printStackTrace();
			} catch (ReflectionException e) {
				e.printStackTrace();
			}
		} 
 }
 //**********************************************************
 // 为app或app的module创建名字为wmName的wm，其组件均为null
 //onceas.work.config:type=WrokManagerConfigAdmin 是一个单独的JMX服务
 //用来创建wm
 //**********************************************************
 public void createWorkManager(String appName,String wmName){
	 createWorkManager(appName,null,wmName);
 }
 
 public void createWorkManager(String appName,String moduleName,String wmName){
	 ObjectName objName = constructObjcetName(appName, moduleName);
	 log.debug("Start creating WorkManager(name=" + wmName + ") for [" + objName + "] .");
	 
	 //create wm.xml and configs if any
	if(! MServerProxy.getMBeanServer().isRegistered(objName)){
		ObjectName wmConfigBossName = getWorkManagerConfigBossObjectName();
		 log.debug("Start createing WEB-INF/wm.xml file through " + wmConfigBossName);
		 try {
				MServerProxy.getMBeanServer().invoke(wmConfigBossName, "createAndParseXml", new Object[]{appName}, new String[]{"java.lang.String"});
			} catch (Exception e) {
				throw new RuntimeException("Exception occurs when invoke createWorkManager for " + wmName + " of " + appName,e);
			}  
	}
	 
	 try {
		MServerProxy.getMBeanServer().invoke(objName, "createWorkManager", new Object[]{wmName}, new String[]{"java.lang.String"});
	} catch (Exception e) {
		throw new RuntimeException("Exception occurs when invoke createWorkManager for " + wmName + " of " + appName,e);
	} 
	 log.debug("End creating WorkManager(name=" + wmName + ") for [" + objName + "].");
	/* String name = "onceas.work.config:type=WrokManagerConfigAdmin";
	 ObjectName objName = null;
	 
	 if(appName == null || wmName == null) 
		 		return;
	 
	 try {
		 objName = new ObjectName(name);
	} catch (MalformedObjectNameException e) {
		e.printStackTrace();
	} catch (NullPointerException e) {
		e.printStackTrace();
	}
	try {
		MServerProxy.getMBeanServer().invoke(objName, "createWorkManager", new Object[]{new String(appName),new String(moduleName),new String(wmName)},
																			new String[]{"String","String","String"});
	} catch (InstanceNotFoundException e) {
		e.printStackTrace();
	} catch (MBeanException e) {
		e.printStackTrace();
	} catch (ReflectionException e) {
		e.printStackTrace();
	}*/
	
 }
 private ObjectName getWorkManagerConfigBossObjectName() {
	 if(workManagerConfigBossObjName == null){
		 try {
			workManagerConfigBossObjName = new ObjectName(WORKMANAGER_CONFIG_BOSS_OBJECT_NAME);
		} catch (Exception e) {
			throw new RuntimeException("Exception occurs when creating ObjectName[" + WORKMANAGER_CONFIG_BOSS_OBJECT_NAME + "].",e);
		} 
	 }
	return workManagerConfigBossObjName;
}
/**
  * 在调用此方法时，已经完成了wm的创建，和wm组件的创建，这个方法仅是将wm与wm组件关联
  * @param appName
  * @param moduleName
  * @param wmName
  * @param capacityConstraint
  * @param maxThreadsConstraint
  * @param minThreadsConstraint
  * @param requestClass
  */
 
 public void createWorkManager(String appName,String moduleName,String wmName,String capacityConstraint,String maxThreadsConstraint,String minThreadsConstraint,String requestClass){
	// ObjectName objName = constructObjcetName(appName, moduleName, wmName);
	 //when creating, the objName does not exist?? who,when and how to create and register this wm????
	 //furthermore  who,when and how to create and register wm component?
	 ObjectName objName = constructObjcetName(appName, moduleName);	
	 if(objName!=null){
			try {
				MServerProxy.getMBeanServer().invoke(objName, "createWorkManager",
													new Object[]{new String(appName),new String(moduleName),new String(wmName),
																	new String(capacityConstraint),new String(minThreadsConstraint),new String(requestClass)},
													new String[]{"String","String","String","String","String","String","String"});
			} catch (InstanceNotFoundException e) {
				e.printStackTrace();
			} catch (MBeanException e) {
				e.printStackTrace();
			} catch (ReflectionException e) {
				e.printStackTrace();
			}
		} 
 }
 
    //request class
 public String getRequestClassType(String appName,String moduleName,String wmName,String fairShareRequestClass){
	 return null;
 }
 //--------------------------------------------------
 //fair share  request class
 //-------------------------------------------------

 public String getFairShareRequestClass(String appName,String wmName){
	 return getFairShareRequestClass(appName,null,wmName);
	 
 }
 public String getFairShareRequestClass(String appName,String moduleName,String wmName){
	 	ObjectName objName = constructObjcetName(appName, moduleName);
	 	String fairsharerequestclass=null;
	     try {
	    	 fairsharerequestclass = (String)(MServerProxy.getMBeanServer().invoke(objName, "getFairShareRequestClass",new Object[]{wmName}, new String[]{"java.lang.String"}));
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		} catch (MBeanException e) {
			e.printStackTrace();
		} catch (ReflectionException e) {
			e.printStackTrace();
		}
		
		return fairsharerequestclass;
 
 }
 //the String returned by getFairShareRequestClass and getFairShareRequestClassName is the same
 public String getFairShareRequestClassName(String appName,String moduleName,String wmName,String fairShareRequestClass){
	 
	 return getFairShareRequestClass(moduleName,wmName,appName);
 }
 public String getFairShareRequestClassFair(String appName,String wmName){
	 return getFairShareRequestClassFair(appName,null,wmName);
 }
 public String getFairShareRequestClassFair(String appName,String moduleName,String wmName){
	 	ObjectName objName = constructObjcetName(appName, moduleName);
	 	String fairsharerequestclassfair=null;
	     try {
	    	 fairsharerequestclassfair = (String)MServerProxy.getMBeanServer().invoke(objName, "getFairShareRequestClassFair", new Object[]{wmName}, new String[]{"java.lang.String"});
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		} catch (MBeanException e) {
			e.printStackTrace();
		} catch (ReflectionException e) {
			e.printStackTrace();
		}
		
		return fairsharerequestclassfair;
 }
 public void updateFairShareRequestClass(String appName,String wmName,String fairShareRequestClass,String fairShareRequestClassShare){
	  updateFairShareRequestClass(appName,null,wmName,fairShareRequestClass,fairShareRequestClassShare);
 }
 public void updateFairShareRequestClass(String appName,String moduleName,String wmName,String fairShareRequestClass,String fairShareRequestClassShare){
	 	ObjectName objName = constructObjcetName(appName, moduleName);
	 	try {
			MServerProxy.getMBeanServer().invoke(objName, "updateFairShareRequestClass", new Object[]{wmName,fairShareRequestClassShare},
																	new String[]{"java.lang.String","java.lang.String"});
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		} catch (MBeanException e) {
			e.printStackTrace();
		} catch (ReflectionException e) {
			e.printStackTrace();
		}
 }
 public void delFairShareRequestClass(String appName,String wmName,String fairShareRequestClass){
	 delFairShareRequestClass(appName, null, wmName, fairShareRequestClass);
 }
 public void delFairShareRequestClass(String appName,String moduleName,String wmName,String fairShareRequestClass){
	 	ObjectName objName = constructObjcetName(appName, moduleName);
	 	try {
			MServerProxy.getMBeanServer().invoke(objName, "delFairShareRequestClass", new Object[]{wmName},
																	new String[]{"java.lang.String"});
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		} catch (MBeanException e) {
			e.printStackTrace();
		} catch (ReflectionException e) {
			e.printStackTrace();
		}
 }
 public void createFairShareRequestClass(String appName,String wmName,String fairShareRequestClass,String fairShareRequestClassShare){
	 createFairShareRequestClass(appName,null,wmName,fairShareRequestClass,fairShareRequestClassShare);
 }
 public void createFairShareRequestClass(String appName,String moduleName,String wmName,String fairShareRequestClass,String fairShareRequestClassShare){
	 ObjectName objName = constructObjcetName(appName, moduleName);
	 try {
		MServerProxy.getMBeanServer().invoke(objName, "createFairShareRequestClass", new Object[]{wmName,fairShareRequestClass,fairShareRequestClassShare}, 
				 																new String[]{"java.lang.String","java.lang.String","java.lang.String"});
	} catch (InstanceNotFoundException e) {
		e.printStackTrace();
	} catch (MBeanException e) {
		e.printStackTrace();
	} catch (ReflectionException e) {
		e.printStackTrace();
	}
 }
 //-----------------------------------------------------
 //response time request class
 //-----------------------------------------------------
 public String getResponseTimeRequestClass(String appName,String wmName){
	 return getResponseTimeRequestClassName(appName, null, wmName);
 }

 public String getResponseTimeRequestClassName(String appName,String moduleName,String wmName){
		ObjectName objName = constructObjcetName(appName, moduleName);
	 	String responsetimerequestclass=null;
	     try {
	    	 responsetimerequestclass = (String)(MServerProxy.getMBeanServer().invoke(objName, "getResponseTimeRequestClass",new Object[]{wmName}, new String[]{"java.lang.String"}));
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		} catch (MBeanException e) {
			e.printStackTrace();
		} catch (ReflectionException e) {
			e.printStackTrace();
		}
		
		return responsetimerequestclass;
 }
 
 public String getResponseTimeRequestClassGoalTime(String appName,String wmName){
	 return getResponseTimeRequestClassGoalTime(appName,null,wmName);
	 
 }
 public String getResponseTimeRequestClassGoalTime(String appName,String moduleName,String wmName){
		ObjectName objName = constructObjcetName(appName, moduleName);
	 	String responsetimerequestclassgoaltime=null;
	     try {
	    	 responsetimerequestclassgoaltime = (String)(MServerProxy.getMBeanServer().invoke(objName, "getResponseTimeRequestClassGoalTime",new Object[]{wmName}, new String[]{"java.lang.String"}));
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		} catch (MBeanException e) {
			e.printStackTrace();
		} catch (ReflectionException e) {
			e.printStackTrace();
		}
		
		return responsetimerequestclassgoaltime;
 }
 public void updateResponseTimeRequestClass(String appName,String wmName,String responseTimeRequestClassName ,String responseTimeRequestClassGoalTime){
	 	updateResponseTimeRequestClass(appName, null, wmName, responseTimeRequestClassName,responseTimeRequestClassGoalTime);
	 
 }
 public void updateResponseTimeRequestClass(String appName,String moduleName,String wmName,String responseTimeRequestClassName,String responseTimeRequestClassGoalTime){
	//parameter  responseTimeRequestClassName is useless because of it is unique in a specific wm
	 ObjectName objName = constructObjcetName(appName, moduleName);
	 try {
		MServerProxy.getMBeanServer().invoke(objName, "updateResponseTimeRequestClass", new Object[]{wmName,responseTimeRequestClassGoalTime}, new String[]{"java.lang.String","java.lang.String"});
	} catch (InstanceNotFoundException e) {
		e.printStackTrace();
	} catch (MBeanException e) {
		e.printStackTrace();
	} catch (ReflectionException e) {
		e.printStackTrace();
	}
 }
 public void delResponseTimeRequestClass(String appName,String wmName,String responseTimeRequestClassName){
	 delResponseTimeRequestClass(appName, null, wmName,responseTimeRequestClassName);
 }
 public void delResponseTimeRequestClass(String appName,String moduleName,String wmName,String responseTimeRequestClassName){
	 ObjectName objName = constructObjcetName(appName, moduleName);
	 try {
		MServerProxy.getMBeanServer().invoke(objName, "delResponseTimeRequestClass", new Object[]{wmName}, new String[]{"java.lang.String"});
	} catch (InstanceNotFoundException e) {
		e.printStackTrace();
	} catch (MBeanException e) {
		e.printStackTrace();
	} catch (ReflectionException e) {
		e.printStackTrace();
	}
 }
 public void createResponseTimeRequestClass(String appName,String wmName,String responseTimeRequestClassName,String responseTimeRequestClassGoalTime){
	 createResponseTimeRequestClass(appName, null, wmName,responseTimeRequestClassName, responseTimeRequestClassGoalTime);
 }
 public void createResponseTimeRequestClass(String appName,String moduleName,String wmName,String responseTimeRequestClassName,String responseTimeRequestClassGoalTime){
	 ObjectName objName = constructObjcetName(appName, moduleName);
	 try {
		MServerProxy.getMBeanServer().invoke(objName, "createResponseTimeRequestClass", new Object[]{wmName,responseTimeRequestClassName,responseTimeRequestClassGoalTime}, new String[]{"java.lang.String","java.lang.String","java.lang.String"});
	} catch (InstanceNotFoundException e) {
		e.printStackTrace();
	} catch (MBeanException e) {
		e.printStackTrace();
	} catch (ReflectionException e) {
		e.printStackTrace();
	}
 }
 
 //------------------------------------------------
//context request class
 //??????
 //------------------------------------------------
 
 
  //constraints
   
 //---------------------------------------------
  //capacity
 //----------------------------------------------
 public String getCapacityConstraint(String appName,String wmName){
	 return getCapacityConstraint(appName, null, wmName);
 }
 public String getCapacityConstraint(String appName,String moduleName,String wmName){
	 ObjectName objName = constructObjcetName(appName, moduleName);
	 String capacity = null;
	 try {
		 capacity = (String)(MServerProxy.getMBeanServer().invoke(objName, "getCapacityConstraint",new Object[]{wmName}, new String[]{"java.lang.String"}));
	} catch (InstanceNotFoundException e) {
		e.printStackTrace();
	} catch (MBeanException e) {
		e.printStackTrace();
	} catch (ReflectionException e) {
		e.printStackTrace();
	}
	return capacity;
 }
 public String getCapacityConstraintName(String appName,String moduleName,String wmName,String capacityConstraint){
	 return null;
 }
 public String getCapacityConstraintCount(String appName,String wmName){
	 return getCapacityConstraintCount(appName,null,wmName);
 }
 public String getCapacityConstraintCount(String appName,String moduleName,String wmName){
	 ObjectName objName = constructObjcetName(appName, moduleName);
	 String capacityCount = null;
	 try {
		 capacityCount = (String)(MServerProxy.getMBeanServer().invoke(objName, "getCapacityConstraintCount",new Object[]{wmName}, new String[]{"java.lang.String"}));
	} catch (InstanceNotFoundException e) {
		e.printStackTrace();
	} catch (MBeanException e) {
		e.printStackTrace();
	} catch (ReflectionException e) {
		e.printStackTrace();
	}
	return capacityCount;
 }
 public void updateCapacityConstraint(String appName,String wmName,String capacityConstraint,String capacityCount){
	 updateCapacityConstraint(appName,null,wmName,capacityConstraint,capacityCount);
 }
 public void updateCapacityConstraint(String appName,String moduleName,String wmName,String capacityConstraint,String capacityCount){
	 ObjectName objName = constructObjcetName(appName, moduleName);
	 try {
		MServerProxy.getMBeanServer().invoke(objName, "updateCapacityConstraint", new Object[]{wmName,capacityCount}, new String[]{"java.lang.String","java.lang.String"});
	} catch (InstanceNotFoundException e) {
		e.printStackTrace();
	} catch (MBeanException e) {
		e.printStackTrace();
	} catch (ReflectionException e) {
		e.printStackTrace();
	}
 }
 public void delCapacityConstraint(String appName,String wmName,String capacityConstraint){
	 delCapacityConstraint(appName,null,wmName,capacityConstraint);
	}
 public void delCapacityConstraint(String appName,String moduleName,String wmName,String capacityConstraint){
	 ObjectName objName = constructObjcetName(appName, moduleName);
	 try {
		MServerProxy.getMBeanServer().invoke(objName, "delCapacityConstraint", new Object[]{wmName}, new String[]{"java.lang.String"});
	} catch (InstanceNotFoundException e) {
		e.printStackTrace();
	} catch (MBeanException e) {
		e.printStackTrace();
	} catch (ReflectionException e) {
		e.printStackTrace();
	} 
 }
 public void createCapacityConstraint(String appName,String wmName,String capacityConstraint,String capacityCount){
	 createCapacityConstraint(appName,null,wmName,capacityConstraint,capacityCount);
 }
 public void createCapacityConstraint(String appName,String moduleName,String wmName,String capacityConstraint,String capacityCount){
	 ObjectName objName = constructObjcetName(appName, moduleName);
	 try {
		MServerProxy.getMBeanServer().invoke(objName, "createCapacityConstraint", new Object[]{wmName,capacityConstraint,capacityCount}, new String[]{"java.lang.String","java.lang.String","java.lang.String"});
	} catch (InstanceNotFoundException e) {
		e.printStackTrace();
	} catch (MBeanException e) {
		e.printStackTrace();
	} catch (ReflectionException e) {
		e.printStackTrace();
	}
 }
 //--------------------------------------------------
 //maxthreads
 //-------------------------------------------------
 public String getMaxThreadsConstraint(String appName,String wmName){
	 return getMaxThreadsConstraint(appName,null,wmName);
 }
 public String getMaxThreadsConstraint(String appName,String moduleName,String wmName){
	 ObjectName objName = constructObjcetName(appName, moduleName);
	 String mtc = null;
	 try {
		mtc = (String)(MServerProxy.getMBeanServer().invoke(objName, "getMaxThreadsConstraint",new Object[]{wmName}, new String[]{"java.lang.String"}));
	} catch (InstanceNotFoundException e) {
		e.printStackTrace();
	} catch (MBeanException e) {
		e.printStackTrace();
	} catch (ReflectionException e) {
		e.printStackTrace();
	}
	return mtc;
 }
 public String getMaxThreadsConstraintName(String appName,String moduleName,String wmName,String maxThreadsConstraint){
	 return getMaxThreadsConstraint(appName, moduleName, wmName);
 }
 public String getMaxThreadsConstraintCount(String appName,String wmName ){
	 return getMaxThreadsConstraintCount(appName,null,wmName);
 }
 public String getMaxThreadsConstraintCount(String appName,String moduleName,String wmName ){
	 ObjectName objName = constructObjcetName(appName, moduleName);
	 String mtcc = null;
	 try {
		mtcc = (String)(MServerProxy.getMBeanServer().invoke(objName, "getMaxThreadsConstraintCount",new Object[]{wmName}, new String[]{"java.lang.String"}));
	} catch (InstanceNotFoundException e) {
		e.printStackTrace();
	} catch (MBeanException e) {
		e.printStackTrace();
	} catch (ReflectionException e) {
		e.printStackTrace();
	}
	return mtcc;
 }
 public void updateMaxThreadsConstraint(String appName,String wmName,String maxThreadsConstraint,String maxThreadsConstraintCount){
	 updateMaxThreadsConstraint(appName,null,wmName,maxThreadsConstraint,maxThreadsConstraintCount);
 }
public void updateMaxThreadsConstraint(String appName,String moduleName,String wmName,String maxThreadsConstraint,String maxThreadsConstraintCount){
	 ObjectName objName = constructObjcetName(appName, moduleName);
	 System.out.println("objName = " + objName);
	 try {
		MServerProxy.getMBeanServer().invoke(objName, "updateMaxThreadsConstraint", new Object[]{wmName,maxThreadsConstraintCount}, new String[]{"java.lang.String","java.lang.String"});
	} catch (InstanceNotFoundException e) {
		e.printStackTrace();
	} catch (MBeanException e) {
		e.printStackTrace();
	} catch (ReflectionException e) {
		e.printStackTrace();
	}
 }
public void delMaxThreadsConstraint(String appName,String wmName,String maxThreadsConstraint){
	delMaxThreadsConstraint(appName,null,wmName,maxThreadsConstraint);
}
 public void delMaxThreadsConstraint(String appName,String moduleName,String wmName,String maxThreadsConstraint){
	 ObjectName objName = constructObjcetName(appName, moduleName);
	 try {
		MServerProxy.getMBeanServer().invoke(objName, "delMaxThreadsConstraint", new Object[]{wmName}, new String[]{"java.lang.String"});
	} catch (InstanceNotFoundException e) {
		e.printStackTrace();
	} catch (MBeanException e) {
		e.printStackTrace();
	} catch (ReflectionException e) {
		e.printStackTrace();
	}
 }
 public void createMaxThreadsConstraint(String appName,String wmName,String maxThreadsConstraint,String maxThreadsConstraintCount){
	 createMaxThreadsConstraint(appName,null,wmName,maxThreadsConstraint,maxThreadsConstraintCount);
 }
 public void createMaxThreadsConstraint(String appName,String moduleName,String wmName,String maxThreadsConstraint,String maxThreadsConstraintCount){
	 ObjectName objName = constructObjcetName(appName, moduleName);
	 try {
		MServerProxy.getMBeanServer().invoke(objName, "createMaxThreadsConstraint", new Object[]{wmName,maxThreadsConstraint,maxThreadsConstraintCount}, new String[]{"java.lang.String","java.lang.String","java.lang.String"});
	} catch (InstanceNotFoundException e) {
		e.printStackTrace();
	} catch (MBeanException e) {
		e.printStackTrace();
	} catch (ReflectionException e) {
		e.printStackTrace();
	}
 }
 //--------------------------------------------------
 //minthreads
 //-------------------------------------------------

 public String getMinThreadsConstraint(String appName,String wmName){
	 return getMinThreadsConstraint(appName,null,wmName);
 }

public String getMinThreadsConstraint(String appName,String moduleName,String wmName){
	 ObjectName objName = constructObjcetName(appName, moduleName);
	 String mtc = null;
	 try {
		mtc = (String)(MServerProxy.getMBeanServer().invoke(objName, "getMinThreadsConstraint",new Object[]{wmName}, new String[]{"java.lang.String"}));
	} catch (InstanceNotFoundException e) {
		e.printStackTrace();
	} catch (MBeanException e) {
		e.printStackTrace();
	} catch (ReflectionException e) {
		e.printStackTrace();
	}
	return mtc;
}
 public String getMinThreadsConstraintName(String moduleName,String wmName,String minThreadsConstraint){
	 return null;
 }
 public String getMinThreadsConstraintCount(String appName,String wmName){
	 return getMinThreadsConstraintCount(appName,null,wmName);
 }
 public String getMinThreadsConstraintCount(String appName,String moduleName,String wmName){
	 ObjectName objName = constructObjcetName(appName, moduleName);
	 String mtcc = null;
	 try {
		mtcc = (String)(MServerProxy.getMBeanServer().invoke(objName, "getMinThreadsConstraintCount",new Object[]{wmName}, new String[]{"java.lang.String"}));
	} catch (InstanceNotFoundException e) {
		e.printStackTrace();
	} catch (MBeanException e) {
		e.printStackTrace();
	} catch (ReflectionException e) {
		e.printStackTrace();
	}
	return mtcc;
 }
 public void updateMinThreadsConstraint(String appName,String wmName,String minThreadsConstraint,String minThreadsConstraintCount){
	 updateMinThreadsConstraint(appName,null,wmName,minThreadsConstraint,minThreadsConstraintCount);
 }
 public void updateMinThreadsConstraint(String appName,String moduleName,String wmName,String minThreadsConstraint,String minThreadsConstraintCount){
	 ObjectName objName = constructObjcetName(appName, moduleName);
	 try {
		MServerProxy.getMBeanServer().invoke(objName, "updateMinThreadsConstraint", new Object[]{wmName,minThreadsConstraintCount}, new String[]{"java.lang.String","java.lang.String"});
	} catch (InstanceNotFoundException e) {
		e.printStackTrace();
	} catch (MBeanException e) {
		e.printStackTrace();
	} catch (ReflectionException e) {
		e.printStackTrace();
	}
 }
 public void delMinThreadsConstraint(String appName,String wmName,String minThreadsConstraint){
	 delMinThreadsConstraint(appName,null,wmName,minThreadsConstraint);
 }
 public void delMinThreadsConstraint(String appName,String moduleName,String wmName,String minThreadsConstraint){
	 ObjectName objName = constructObjcetName(appName, moduleName);
	 try {
		MServerProxy.getMBeanServer().invoke(objName, "delMinThreadsConstraint", new Object[]{wmName}, new String[]{"java.lang.String"});
	} catch (InstanceNotFoundException e) {
		e.printStackTrace();
	} catch (MBeanException e) {
		e.printStackTrace();
	} catch (ReflectionException e) {
		e.printStackTrace();
	}
 }
 public void createMinThreadsConstraint(String appName,String wmName,String minThreadsConstraint,String minThreadsConstraintCount){
	 createMinThreadsConstraint(appName,null,wmName,minThreadsConstraint,minThreadsConstraintCount);
 }
 public void createMinThreadsConstraint(String appName,String moduleName,String wmName,String minThreadsConstraint,String minThreadsConstraintCount){
	 ObjectName objName = constructObjcetName(appName, moduleName);
	 try {
		MServerProxy.getMBeanServer().invoke(objName, "createMinThreadsConstraint", new Object[]{wmName,minThreadsConstraint,minThreadsConstraintCount}, new String[]{"java.lang.String","java.lang.String","java.lang.String"});
	} catch (InstanceNotFoundException e) {
		e.printStackTrace();
	} catch (MBeanException e) {
		e.printStackTrace();
	} catch (ReflectionException e) {
		e.printStackTrace();
	}
 }
public void save(String appName) {
	 ObjectName objName = constructObjcetName(appName, null);
	 try {
		MServerProxy.getMBeanServer().invoke(objName, "save", null, null);
	} catch (InstanceNotFoundException e) {
		e.printStackTrace();
	} catch (MBeanException e) {
		e.printStackTrace();
	} catch (ReflectionException e) {
		e.printStackTrace();
	}
}
 
}
