package com.onceas.wm.console.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.onceas.wm.console.model.WorkManagerBean;

/**
 * @author syk
 *
 */
public class WorkManagerUtil {
	
	private final static String SEPARATOR = Constant.SEPARATOR;

	private static WebAppWorkManagerXmlConfigOp webappWmXmlConfig = new WebAppWorkManagerXmlConfigOp();
	/**
	 * 返回给定应用配置的WorkManager列表
	 * @param appName
	 * @return 
	 */
	public static List<WorkManagerBean> getWorkManagersFor(String appName){
		String[] wms = webappWmXmlConfig.getWorkManagers(appName);
		if(wms.length == 0){
			return Collections.emptyList();
		}
		List<WorkManagerBean> result = new ArrayList<WorkManagerBean>();
		for(String wmName : wms){
			String type = webappWmXmlConfig.getWorkManagerType(appName, wmName);
			WorkManagerBean bean = new WorkManagerBean(wmName, type);
			result.add(bean);
		}
			return result;
	}
	
	 public static void delWorkManager(String appName,String wmName){
		 webappWmXmlConfig.delWorkManager(appName,wmName);
	 }
	 
	 public static void persist(String appName){
		 webappWmXmlConfig.save(appName);
	 }
	 public static String buildWmContext(String webAppName, String wmName){
		 return webAppName + SEPARATOR + wmName ;
	 }
	 
	 public static String[] parseContext(String context){
		 return context.split(SEPARATOR);
	 }
	 
	 /**
	  * 根据appName、wmName从WorkManagerConfig中查询wm[maxName, maxValue]，组装为WorkManagerBean
	  * @param appName
	  * @param wmName
	  * @return
	  */
	 public static WorkManagerBean fetchWorkManagerBean(String appName, String wmName) {
//		 String maxThreadConstraintName = webappWmXmlConfig.getMaxThreadsConstraint(appName, wmName);
//		 if(maxThreadConstraintName == null ){
//			 throw new RuntimeException("MaxConstraintName of WorkManager["+ wmName + "] for application[" + appName + "] is NULL.");
//		 }
//		 String maxThreadConstraintValue = webappWmXmlConfig.getMaxThreadsConstraintCount(appName, wmName);
//		 if(maxThreadConstraintValue == null ){
//			 throw new RuntimeException("MaxConstraintValue of WorkManager["+ wmName + "] for application[" + appName + "] is NULL.");
//		 }
//		 String minThreadConstraintName = webappWmXmlConfig.getMinThreadsConstraint(appName, wmName);
//		 if(minThreadConstraintName == null ){
//			 throw new RuntimeException("MaxConstraintName of WorkManager["+ wmName + "] for application[" + appName + "] is NULL.");
//		 }
//		 String minThreadConstraintValue = webappWmXmlConfig.getMaxThreadsConstraintCount(appName, wmName);
//		 if(minThreadConstraintValue == null ){
//			 throw new RuntimeException("MaxConstraintValue of WorkManager["+ wmName + "] for application[" + appName + "] is NULL.");
//		 }
//		 
//		 String capacityConstraintName = webappWmXmlConfig.getMinThreadsConstraint(appName, wmName);
//		 if(capacityConstraintName == null ){
//			 throw new RuntimeException("MaxConstraintName of WorkManager["+ wmName + "] for application[" + appName + "] is NULL.");
//		 }
//		 String capacityConstraintValue = webappWmXmlConfig.getMaxThreadsConstraintCount(appName, wmName);
//		 if(capacityConstraintValue == null ){
//			 throw new RuntimeException("MaxConstraintValue of WorkManager["+ wmName + "] for application[" + appName + "] is NULL.");
//		 }
		String maxThreadsConstraintName = (webappWmXmlConfig.getMaxThreadsConstraint(appName,wmName)==null? "" :webappWmXmlConfig.getMaxThreadsConstraint(appName,wmName));
		String maxThreadsConstraintCount = (webappWmXmlConfig.getMaxThreadsConstraintCount(appName,wmName)==null? "" :webappWmXmlConfig.getMaxThreadsConstraintCount(appName,wmName));
		
		String minThreadsConstraintName = (webappWmXmlConfig.getMinThreadsConstraint(appName,wmName)==null? "" : webappWmXmlConfig.getMinThreadsConstraint(appName,wmName));
		String minThreadsConstraintCount = (webappWmXmlConfig.getMinThreadsConstraintCount(appName,wmName)==null? "" : webappWmXmlConfig.getMinThreadsConstraintCount(appName,wmName));
		
		String capacityConstraintName = (webappWmXmlConfig.getCapacityConstraint(appName,wmName)==null? "" : webappWmXmlConfig.getCapacityConstraint(appName,wmName));
		String capacityConstraintCount = (webappWmXmlConfig.getCapacityConstraintCount(appName,wmName)==null? "" : webappWmXmlConfig.getCapacityConstraintCount(appName,wmName));
		
		String fairShareRequestClassName = (webappWmXmlConfig.getFairShareRequestClass(appName,wmName)==null? "" : webappWmXmlConfig.getFairShareRequestClass(appName,wmName));
		String fairShare = (webappWmXmlConfig.getFairShareRequestClassFair(appName,wmName)==null? "" : webappWmXmlConfig.getFairShareRequestClassFair(appName,wmName));
		
		String responseTimeRequestClassName = ( webappWmXmlConfig.getResponseTimeRequestClass(appName,wmName) == null? "" :webappWmXmlConfig.getResponseTimeRequestClass(appName,wmName));
		String goalTime = ( webappWmXmlConfig.getResponseTimeRequestClassGoalTime(appName,wmName) == null? "" :webappWmXmlConfig.getResponseTimeRequestClassGoalTime(appName,wmName));

		 WorkManagerBean wmBean = new WorkManagerBean();
		 
		 wmBean.setName(wmName);
		 
		 wmBean.setMaxThreadConstraintName(maxThreadsConstraintName);
		 try{
			 int value = Integer.parseInt(maxThreadsConstraintCount);
			 wmBean.setMaxThreadConstraintValue(value);
		 }catch(Exception e){}
		 
		 wmBean.setMinThreadConstraintName(minThreadsConstraintName);
		 try{
			 int value = Integer.parseInt(minThreadsConstraintCount);
			 wmBean.setMinThreadConstraintValue(value);
		 }catch(Exception e){}
		 
		 //wmBean.setMinThreadConstraintValue(Integer.parseInt(minThreadsConstraintCount));
		 wmBean.setCapacityConstraintName(capacityConstraintName);
		 try{
			 int value = Integer.parseInt(capacityConstraintCount);
			 wmBean.setCapacityConstraintValue(value);
		 }catch(Exception e){}
		 //wmBean.setCapacityConstraintValue(Integer.parseInt(capacityConstraintCount));
		 wmBean.setFairShareRequestClassName(fairShareRequestClassName);
		 try{
			 int value = Integer.parseInt(fairShare);
			 wmBean.setFairShare(value);
		 }catch(Exception e){}
		 
		 wmBean.setResponseTimeRequestClassName(responseTimeRequestClassName);
		 try{
			 int value = Integer.parseInt(goalTime);
			 wmBean.setGoalTime(value);
		 }catch(Exception e){}
		 
		 return wmBean;
		//return new WorkManagerBean(wmName, maxConstraintName, Integer.parseInt(maxConstraintValue));
	 }
	 
	 /**
	  *  根据workmanagerbean 通过WorkManagerConfig，为指定应用创建workmanager
	  * @param wmBean
	  */
	 public static void createWorkManager(String appName,WorkManagerBean wmBean) {
		String wmName = wmBean.getName();
		webappWmXmlConfig.createWorkManager(appName, wmName);
		if(wmBean.getMaxThreadConstraintName()!=null&&wmBean.getMaxThreadConstraintName().length()>0)
		webappWmXmlConfig.createMaxThreadsConstraint(appName, wmName, wmBean.getMaxThreadConstraintName(), wmBean.getMaxThreadConstraintValue() + "");
		if(wmBean.getMinThreadConstraintName()!=null&&wmBean.getMinThreadConstraintName().length()>0)
		webappWmXmlConfig.createMinThreadsConstraint(appName, wmBean.getName(), wmBean.getMinThreadConstraintName(), wmBean.getMinThreadConstraintValue()+"");
		if(wmBean.getCapacityConstraintName()!=null&&wmBean.getCapacityConstraintName().length()>0)
		webappWmXmlConfig.createCapacityConstraint(appName, wmBean.getName(), wmBean.getCapacityConstraintName(), wmBean.getCapacityConstraintValue()+"");
		if(wmBean.getFairShareRequestClassName()!=null&&wmBean.getFairShareRequestClassName().length()>0)
		webappWmXmlConfig.createFairShareRequestClass(appName, wmBean.getName(),wmBean.getFairShareRequestClassName(), wmBean.getFairShare()+"");
		if(wmBean.getResponseTimeRequestClassName()!=null&&wmBean.getResponseTimeRequestClassName().length()>0)
		webappWmXmlConfig.createResponseTimeRequestClass(appName, wmBean.getName(), wmBean.getResponseTimeRequestClassName(),wmBean.getGoalTime()+"");
	}
	 
//	 public static void updateWorkManager(String appName,WorkManagerBean wmBean) {
//		if(wmBean.getMaxThreadConstraintName()!=null&&wmBean.getMaxThreadConstraintName().length()>0)
//		webappWmXmlConfig.updateMaxThreadsConstraint(appName, wmBean.getName(), wmBean.getMaxThreadConstraintName(), wmBean.getMaxThreadConstraintValue()+"");
//		if(wmBean.getMinThreadConstraintName()!=null&&wmBean.getMinThreadConstraintName().length()>0)
//		webappWmXmlConfig.updateMinThreadsConstraint(appName, wmBean.getName(), wmBean.getMinThreadConstraintName(), wmBean.getMinThreadConstraintValue()+"");
//		if(wmBean.getCapacityConstraintName()!=null&&wmBean.getCapacityConstraintName().length()>0)
//		webappWmXmlConfig.updateCapacityConstraint(appName, wmBean.getName(), wmBean.getCapacityConstraintName(), wmBean.getCapacityConstraintValue()+"");
//		if(wmBean.getFairShareRequestClassName()!=null&&wmBean.getFairShareRequestClassName().length()>0)
//		webappWmXmlConfig.updateFairShareRequestClass(appName, wmBean.getName(),wmBean.getFairShareRequestClassName(), wmBean.getFairShare()+"");
//		if(wmBean.getResponseTimeRequestClassName()!=null&&wmBean.getResponseTimeRequestClassName().length()>0)
//		webappWmXmlConfig.updateResponseTimeRequestClass(appName, wmBean.getName(), wmBean.getResponseTimeRequestClassName(),wmBean.getGoalTime()+"");
//	}
	
	 public static void delMaxThreadsConstraint(String appName,WorkManagerBean wmBean){
		 webappWmXmlConfig.delMaxThreadsConstraint(appName, wmBean.getName(), wmBean.getMaxThreadConstraintName());
	 }
	 public static void delMinThreadsConstraint(String appName,WorkManagerBean wmBean){
		 webappWmXmlConfig.delMinThreadsConstraint(appName, wmBean.getName(), wmBean.getMinThreadConstraintName());
	 }
	 public static void delCapacityConstraint(String appName,WorkManagerBean wmBean){
		 webappWmXmlConfig.delCapacityConstraint(appName, wmBean.getName(), wmBean.getCapacityConstraintName());
	 }
	 public static void delFairShareRequestClass(String appName,WorkManagerBean wmBean){
		 webappWmXmlConfig.delFairShareRequestClass(appName, wmBean.getName(), wmBean.getFairShareRequestClassName());
	 }
	 public static void delResponseTimeRequestClass(String appName,WorkManagerBean wmBean){
		 webappWmXmlConfig.delResponseTimeRequestClass(appName, wmBean.getName(), wmBean.getResponseTimeRequestClassName());
	 }
	 
	 public static void updateMaxThreadsConstraint(String appName,WorkManagerBean wmBean,boolean update){
		 if(wmBean.getMaxThreadConstraintName()==null||wmBean.getMaxThreadConstraintName().length()==0)return;
		 String wmName = wmBean.getName();
		 String name = wmBean.getMaxThreadConstraintName();
		 String value = wmBean.getMaxThreadConstraintValue()+"";
		 if(update){
			webappWmXmlConfig.updateMaxThreadsConstraint(appName,wmName, name, value);	
		 }else{
			webappWmXmlConfig.createMaxThreadsConstraint(appName,wmName, name, value);		
		 }
	}
	 public static void updateMinThreadsConstraint(String appName,WorkManagerBean wmBean,boolean update){
		 if(wmBean.getMinThreadConstraintName()==null||wmBean.getMinThreadConstraintName().length()==0)return;
		 String wmName = wmBean.getName();
		 String name = wmBean.getMinThreadConstraintName();
		 String value = wmBean.getMinThreadConstraintValue()+"";
		 if(update){
			webappWmXmlConfig.updateMinThreadsConstraint(appName,wmName, name,value);	
		 }else{
			webappWmXmlConfig.createMinThreadsConstraint(appName,wmName, name, value);		
		 }
	}
	 public static void updateCapacityConstraint(String appName,WorkManagerBean wmBean,boolean update){
		 if(wmBean.getCapacityConstraintName()==null||wmBean.getCapacityConstraintName().length()==0)return;
		 String wmName = wmBean.getName();
		 String name = wmBean.getCapacityConstraintName();
		 String value = wmBean.getCapacityConstraintValue()+"";
		 if(update){
			webappWmXmlConfig.updateCapacityConstraint(appName,wmName, name,value);	
		 }else{
			webappWmXmlConfig.createCapacityConstraint(appName,wmName, name, value);		
		 }
	}
	 public static void updateFairShareRequestClass(String appName,WorkManagerBean wmBean,boolean update){
		 if(wmBean.getFairShareRequestClassName()==null||wmBean.getFairShareRequestClassName().length()==0)return;
		 String wmName = wmBean.getName();
		 String name = wmBean.getFairShareRequestClassName();
		 String value = wmBean.getFairShare()+"";
		 if(update){
			webappWmXmlConfig.updateFairShareRequestClass(appName,wmName, name,value);	
		 }else{
			webappWmXmlConfig.createFairShareRequestClass(appName,wmName, name, value);		
		 }
	}
	 public static void updateResponseTimeRequestClass(String appName,WorkManagerBean wmBean,boolean update){
		 if(wmBean.getResponseTimeRequestClassName()==null||wmBean.getResponseTimeRequestClassName().length()==0)return;
		 String wmName = wmBean.getName();
		 String name = wmBean.getResponseTimeRequestClassName();
		 String value = wmBean.getGoalTime()+"";
		 if(update){
			webappWmXmlConfig.updateResponseTimeRequestClass(appName,wmName, name,value);	
		 }else{
			webappWmXmlConfig.createResponseTimeRequestClass(appName,wmName, name, value);		
		 }
	}
	 
	 
//	 public static void delWorkManagerAttr(String appName,WorkManagerBean wmBean){
//		 webappWmXmlConfig.delMaxThreadsConstraint(appName, wmBean.getName(), wmBean.getMaxThreadConstraintName());
//		 webappWmXmlConfig.delMinThreadsConstraint(appName, wmBean.getName(), wmBean.getMinThreadConstraintName());
//		 webappWmXmlConfig.delCapacityConstraint(appName, wmBean.getName(), wmBean.getCapacityConstraintName());
//		 webappWmXmlConfig.delFairShareRequestClass(appName, wmBean.getName(), wmBean.getFairShareRequestClassName());
//		 webappWmXmlConfig.delResponseTimeRequestClass(appName, wmBean.getName(), wmBean.getResponseTimeRequestClassName());
//	 }
}
