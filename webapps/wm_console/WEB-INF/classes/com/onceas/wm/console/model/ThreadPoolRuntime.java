package com.onceas.wm.console.model;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;

import com.onceas.wm.console.util.MBeanServerWrapper;
import com.onceas.wm.console.util.MServerProxy;

@SuppressWarnings("unused")
public class ThreadPoolRuntime extends MBeanServerWrapper{

	private static ObjectName objName;

	static {
		try {
			objName = new javax.management.ObjectName(
					"onceas.work.runtime:type=ThreadPool,ThreadPoolRuntime=OnceASThreadPoolRuntime");
		} catch (MalformedObjectNameException e) {
			throw new RuntimeException(e);
		} catch (NullPointerException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * properties
	 */

	private String Name ;

	private int QueueLength ;

	// System.out.println(QueueLength);
//	private Double Throughput1 ;

	long Throughput ;

	// System.out.println(Throughput);
	private int ExecuteThreadTotalCount;

	// System.out.println(ExecuteThreadTotalCount);
	private int ExecuteThreadIdleCount;

	// System.out.println(ExecuteThreadIdleCount);
	private int PendingUserRequestCount ;

	private long RejectedRequestCount ;

	private int ExecuteThreadCount ;
	private int SharedCapacityForWorkManagers ;

	private int HoggingThreadCount ;

	private int StandbyThreadCount ;

	private int MinThreadsConstraintsPending ;

	private long MinThreadsConstraintsCompleted ;

	private long CompletedRequestCount ;
	
	public long getCompletedRequestCount() {
		return  (Long) getAttribute(objName, "CompletedRequestCount");
	}

	public int getExecuteThreadCount() {
		return  (Integer) getAttribute(objName, "ExecuteThreadCount");

	}

	public int getExecuteThreadIdleCount() {
		return  (Integer)getAttribute(objName, "ExecuteThreadIdleCount");
	}

	public int getExecuteThreadTotalCount() {
		return  (Integer)getAttribute(objName, "ExecuteThreadTotalCount");
	}

	public int getHoggingThreadCount() {
		return  (Integer) getAttribute(objName, "HoggingThreadCount");
	}

	public long getMinThreadsConstraintsCompleted() {
		return  (Long) getAttribute(objName,"MinThreadsConstraintsCompleted");
	}

	public int getMinThreadsConstraintsPending() {
		return  (Integer)getAttribute(objName,"MinThreadsConstraintsPending");
	}

	public String getName() {
		return (String)getAttribute(objName, "Name");
	}

	public int getPendingUserRequestCount() {
		return (Integer)getAttribute(objName, "PendingUserRequestCount");
	}

	public int getQueueLength() {
		return (Integer) getAttribute(objName, "QueueLength");
	}

	public long getRejectedRequestCount() {
		return  (Long) getAttribute(objName, "RejectedRequestCount");
	}

	public int getSharedCapacityForWorkManagers() {
		return  (Integer) getAttribute(objName,"SharedCapacityForWorkManagers");
	}

	public int getStandbyThreadCount() {
		return  (Integer) getAttribute(objName, "StandbyThreadCount");
	}

	public long getThroughput() {
		return ((Double)getAttribute(objName, "Throughput")).longValue();
	}
}
