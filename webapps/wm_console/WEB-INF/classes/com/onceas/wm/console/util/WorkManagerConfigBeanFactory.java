package com.onceas.wm.console.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.onceas.wm.console.model.WorkManagerConfigBean;
import com.onceas.work.stat.WorkManagerStat;
import com.onceas.work.stat.WorkManagerStatType;

/**
 * @author syk
 *
 */
public class WorkManagerConfigBeanFactory {

	private static final Log log = LogFactory.getLog(WorkManagerConfigBeanFactory.class);
	
	private static ObjectName globalWorkManagerCollectionsObjName ;
	
	static {
		try {
			globalWorkManagerCollectionsObjName = new ObjectName(Constant.WORK_MANAGER_RUNTIME_MBEAN_DOMAIN + ":"
					+ Constant.RUNTIME_TYPE + "= GlobalWorkManagerCollections");
		} catch (MalformedObjectNameException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 获得所有AppWorkManager
	 */
	@SuppressWarnings("unchecked")
	public static Set<WorkManagerConfigBean> fetchAllApplicationWorkManagerMaxValues(){
		try {
			Map<String, Integer> appWmAndMaxValue = (Map<String, Integer>)invoke("getApplicationWorkManagers", null, null);
		
			if(appWmAndMaxValue.isEmpty()){
				return Collections.emptySet();
			}
			
			return constructWorkManagerConfigBeans(appWmAndMaxValue, null,null, null,null);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	
	}

	private static Map<String, Long> lastAppWmAndCompleteCounts;
	public static Set<WorkManagerConfigBean> fetchAllApplicationWorkManagerCpuTimes(){
		try {
			log.debug("Start fetchAllApplicationWorkManagerCpuTimes.");
			Map<String, Double> appWmAndCpuTime = (Map<String, Double>)invoke("getApplicationWorkManagerCpuTimes", null, null);
			log.debug("Fetched cpu times.");
			Map<String, Integer> appWmAndMaxValue = (Map<String, Integer>)invoke("getApplicationWorkManagers", null, null);
			log.debug("Fetched maximum values.");
			log.debug("Stop fetchAllApplicationWorkManagerCpuTimes.");
			
			Map<String, Integer> appWmAndInProgress = (Map<String, Integer>)invoke("getApplicationWorkManagerInProgress", null, null);
			Map<String, Integer> appWmAndQueueSize = (Map<String, Integer>)invoke("getApplicationWorkManagerQueueSize", null, null);
			Map<String, Long> appWmAndCompleteCount = (Map<String, Long>)invoke("getApplicationWorkManagerComleteCount", null, null);
			
			if(appWmAndCpuTime.isEmpty()){
				return Collections.emptySet();
			}
			
			return constructWorkManagerConfigBeans(appWmAndMaxValue ,appWmAndCpuTime,appWmAndInProgress,appWmAndQueueSize,appWmAndCompleteCount);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	
	}

	private final static String APP_WORKMANAGER_NAME = "AppWorkManager";
	
	private static Set<WorkManagerConfigBean> constructWorkManagerConfigBeans(Map<String, Integer> appWmAndMaxValue,Map<String, Double> appWmAndCpuTime,Map<String, Integer> appWmAndInProgress, Map<String, Integer> appWmAndQueueSize,Map<String, Long> appWmAndCompleteCount) {
		log.debug("Start constructWorkManagerConfigBeans.");
		if(appWmAndCpuTime == null && appWmAndMaxValue == null){
			throw new IllegalArgumentException("At least one parameter is not null.");
		}
		Set<WorkManagerConfigBean> result = new HashSet<WorkManagerConfigBean>();
		
		if(appWmAndCpuTime != null && appWmAndMaxValue != null){
			for(String name : appWmAndCpuTime.keySet()){
				double cputime = appWmAndCpuTime.get(name);
				int value = appWmAndMaxValue.get(name);
				WorkManagerConfigBean bean = new WorkManagerConfigBean(name, APP_WORKMANAGER_NAME,value, cputime);
				if(appWmAndInProgress != null){
					bean.setInProgress(appWmAndInProgress.get(name));
				}
				if(appWmAndQueueSize != null){
					bean.setMaxQueueSize(appWmAndQueueSize.get(name));
				}
				if(appWmAndCompleteCount != null){//compute throughtput
					long count = appWmAndCompleteCount.get(name);
					bean.setCompletedCount(count);
					if(lastAppWmAndCompleteCounts != null){
						bean.setLastCompletedCount(lastAppWmAndCompleteCounts.get(name));
					}
				}
				
				result.add(bean);
			}
			
		}else if(appWmAndCpuTime != null){
			for(String name : appWmAndCpuTime.keySet()){
				double cputime = appWmAndCpuTime.get(name);
				WorkManagerConfigBean bean = new WorkManagerConfigBean(name, APP_WORKMANAGER_NAME, cputime);
				if(appWmAndInProgress != null){
					bean.setInProgress(appWmAndInProgress.get(name));
				}
				if(appWmAndQueueSize != null){
					bean.setMaxQueueSize(appWmAndQueueSize.get(name));
				}
				
				if(appWmAndCompleteCount != null){//compute throughtput
					long count = appWmAndCompleteCount.get(name);
					bean.setCompletedCount(count);
					if(lastAppWmAndCompleteCounts != null){
						bean.setLastCompletedCount(lastAppWmAndCompleteCounts.get(name));
					}
				}
				
				result.add(bean);
			}
		}else{
			for(String name : appWmAndMaxValue.keySet()){
				int value = appWmAndMaxValue.get(name);
				WorkManagerConfigBean bean = new WorkManagerConfigBean(name, APP_WORKMANAGER_NAME, value);
				if(appWmAndInProgress != null){
					bean.setInProgress(appWmAndInProgress.get(name));
				}
				if(appWmAndQueueSize != null){
					bean.setMaxQueueSize(appWmAndQueueSize.get(name));
				}
				
				if(appWmAndCompleteCount != null){//compute throughtput
					long count = appWmAndCompleteCount.get(name);
					bean.setCompletedCount(count);
					if(lastAppWmAndCompleteCounts != null){
						bean.setLastCompletedCount(lastAppWmAndCompleteCounts.get(name));
					}
				}
				
				result.add(bean);
			}
		}

		if(appWmAndCompleteCount != null && !appWmAndCompleteCount.isEmpty()){
			lastAppWmAndCompleteCounts = appWmAndCompleteCount;
		}
		log.debug("Stop constructWorkManagerConfigBeans.");
		return result;
	}

	public static double getTotalCpu(){
		try {
			return (Double)invoke("getTotalCpu", null, null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	private static MBeanServer getMBeanServer(){
		return MServerProxy.getMBeanServer();
	}
	
	private static Object invoke(String operationName, Object params[], String signature[])throws InstanceNotFoundException, MBeanException, ReflectionException{
		return getMBeanServer().invoke(globalWorkManagerCollectionsObjName, operationName, params, signature);
	}
	
	public static Set<WorkManagerConfigBean> fetchAppWorkManagerStats(){
		return buildFromAppWorkManagerStats( false);
	}
	
	public static Set<WorkManagerConfigBean> fetchAppWorkManagerBasicStats(){
		return buildFromAppWorkManagerStats( true);
	}
	
	@SuppressWarnings("unchecked")
	private static Set<WorkManagerConfigBean> buildFromAppWorkManagerStats(boolean isBaisic){
		Map<String, Set<WorkManagerStat>>  statsMap;
		try {
			statsMap = (Map<String, Set<WorkManagerStat>> )invoke("assembleAppWorkManagerStats", null, null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
		
		// 将map的值保存于set
		Set<WorkManagerStat> stats = new HashSet<WorkManagerStat>();
		for(String key : statsMap.keySet()){
			stats.addAll(statsMap.get(key));
		}
		
		return constructWorkManagerConfigBeans(stats, isBaisic);
	}
	/**
	 * @param stats
	 */
	
	private static Map<String,WorkManagerStat> lastStatsMap = new HashMap<String, WorkManagerStat>();
	private static Set<WorkManagerConfigBean> constructWorkManagerConfigBeans(Set<WorkManagerStat> stats, boolean isBaisic) {
		if(stats == null || stats.isEmpty()){
			return Collections.emptySet();
		}
		
		Set<WorkManagerConfigBean> result = new HashSet<WorkManagerConfigBean>();
		for(WorkManagerStat s : stats){
			WorkManagerConfigBean cbean = new WorkManagerConfigBean(s.getAppName(), s.getWmName(), s.getMaxConstraintValue());
			//save attributes
			cbean.setWmType(s.getWmType().toString());
			cbean.setWmNameReferencedByContextConstraint(s.getWmNameReferencedByContextConstraint());
			cbean.setUser(s.getUser());
			cbean.setGroup(s.getGroup());
			
			if(!isBaisic){
				cbean.setCpuValue(s.getCpuValue());
				cbean.setCompletedCount(s.getCompletedCount());
				cbean.setInProgress(s.getInProgress());
				cbean.setMaxQueueSize(s.getMaxQueueSize());

				// for computing throughput
				String an = s.getAppName();
				WorkManagerStat ls = lastStatsMap.get(an);
				if(ls != null){
					cbean.setLastCompletedCount(ls.getCompletedCount());
				}
				
				lastStatsMap.put(an, s);
			}
			
			result.add(cbean);
		}
		
		return result;
	}
	
	public static void updateMaxCountOfAppWorkManager(String appName, String wmName, String wmType,
			String user, String group, int newCount) {
		try {
			WorkManagerStatType type = WorkManagerStatType.toValue(wmType);
			invoke("updateMaxCountOfAppWorkManager", new Object[]{appName,wmName,type, user,group,newCount},  new String[]{String.class.getName(), String.class.getName(),WorkManagerStatType.class.getName(),String.class.getName(),String.class.getName(),int.class.getName()});
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
		
	}
	
}
