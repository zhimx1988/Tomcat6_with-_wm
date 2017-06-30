package com.onceas.wm.console.pages;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.jfree.chart.JFreeChart;

import com.onceas.wm.console.model.ThreadPoolRuntime;
import com.onceas.wm.console.util.WorkManagerAppList;
import com.onceas.wm.console.util.WorkManagerAppStaNode;
import com.onceas.wm.console.util.WorkManagerChartDrawer;
import com.onceas.wm.console.util.WorkManagerDataFiller;
import com.onceas.wm.console.util.WorkManagerNode;

@SuppressWarnings("unused")
public class WorkManagerMonitor {

	private ThreadPoolRuntime threadPoolRuntime;

	private AtomicBoolean tpInited = new AtomicBoolean(false);
	
	@Property
	private WorkManagerAppStaNode workManagerAppStaNode;
	
	@Property
	private int appIndex;
	
	@Property
	private WorkManagerNode wmNode;
	
	@Property
	private int wmNodeIndex;
	
	@InjectComponent
	private Zone wmChartZone;
	
	@InjectComponent
	private Zone throughputChartZone;
	
	@InjectComponent
	private Zone wmTableZone;
	
	@InjectComponent
	private Zone wmInfoZone;
	
	private synchronized  void initThreadPoolRuntime() {
		tpInited.set(true);
		this.threadPoolRuntime = new ThreadPoolRuntime();
	}

	public ThreadPoolRuntime getThreadPoolRuntime() {
//		if(!tpInited.get()){
//			initThreadPoolRuntime();
//		}
		this.threadPoolRuntime = new ThreadPoolRuntime();
		return threadPoolRuntime;
	}
	
	public JFreeChart getWmChart(){
		return WorkManagerChartDrawer.draw();
	}
	
	public JFreeChart getThroughputChart(){
		return WorkManagerChartDrawer.drawThroughput();
	}
	public List<WorkManagerAppStaNode> getWorkManagerAppList(){
		fillWorkmanagerData();
		return WorkManagerAppList.appList;
	}
	
	public boolean isAppOdd(){
		return appIndex % 2 != 0;
	}
	
	public boolean isNodeOdd(){
		return wmNodeIndex % 2 != 0;
	}
	
	private void fillWorkmanagerData(){
		WorkManagerDataFiller.fill();
	}
	
//	@OnEvent(component="zoneTrigger", value="action")
	@OnEvent(component="zoneUpdater")
	Object trigger(){
		return new MultiZoneUpdate("wmChartZone", wmChartZone.getBody()).add("wmInfoZone", wmInfoZone.getBody()).add("wmTableZone", wmTableZone.getBody()).add("throughputChartZone", throughputChartZone);
//		return new MultiZoneUpdate("wmInfoZone", wmInfoZone.getBody());
//		return new MultiZoneUpdate("wmChartZone", wmChartZone.getBody());
//		return new MultiZoneUpdate("wmTableZone", wmTableZone.getBody());
	}
}

