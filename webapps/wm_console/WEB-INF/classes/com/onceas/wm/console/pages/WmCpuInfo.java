package com.onceas.wm.console.pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;

import com.onceas.wm.console.kalman.CPUFilter;
import com.onceas.wm.console.model.WorkManagerConfigBean;
import com.onceas.wm.console.model.WorkManagerSnapshot;
import com.onceas.wm.console.util.ConfigUtil;
import com.onceas.wm.console.util.CpuUtil;
import com.onceas.wm.console.util.WorkManagerConfigBeanFactory;

/**
 * @author syk
 * 
 */
public class WmCpuInfo {

	private static long interval = ConfigUtil.getInterval();

	// private static String configFile = "config.properties";

	// private static Properties properties = null;

	private static final Log log = LogFactory.getLog(WmCpuInfo.class);

	// private static final String INTERVAL_KEY = "interval";

	@InjectComponent
	private Zone wmInfoZone;

	@Property
	private WorkManagerConfigBean configBean;
	
	@Property
	private WorkManagerSnapshot snapshot;
	
	
	@Property
	private int index;

	private static List<WorkManagerConfigBean> internalConfigs;

	private static Random rnd = new Random();

	// static{
	// interval = Long.parseLong(getProperties().getProperty(INTERVAL_KEY));
	// }
	//	
	// public static Properties getProperties() {
	// if (properties != null) {
	// return properties;
	// } else {
	// properties = PropertyLoader.getProperties(configFile);
	// }
	// return properties;
	// }

	public List<WorkManagerSnapshot> getWmSnapshots() {
		return getConfigs();
	}

	private List<WorkManagerConfigBean> getConfigsOfApp(String appName) {
		return null;
	}
	
	private List<WorkManagerSnapshot> getConfigs() {
		log.debug("Start getConfigs.");
//		Set<WorkManagerConfigBean> set = WorkManagerConfigBeanFactory
//				.fetchAllApplicationWorkManagerCpuTimes();
		Set<WorkManagerConfigBean> set = WorkManagerConfigBeanFactory.fetchAppWorkManagerStats();

		List<WorkManagerConfigBean> tmp = new ArrayList<WorkManagerConfigBean>(set);
		log.debug("Start sort.");
		Collections.sort(tmp, new Comparator<WorkManagerConfigBean>() {
			// 按（应用名,wm名）排序
			public int compare(WorkManagerConfigBean o1, WorkManagerConfigBean o2) {
				int compare = o1.getAppName().compareTo(o2.getAppName());
				if(compare != 0){
					return compare;
				}else{
					return o1.getWmName().compareTo(o2.getWmName());
				}
				
			}
		});
		log.debug("Stop sort.");

		// add by huangxiang
		tmp = kalmanFilter(tmp);
		// add over

		// List<WorkManagerConfigBean> result = new
		// ArrayList<WorkManagerConfigBean>(set);
		// log.debug("Start sort.");
		// Collections.sort(result, new Comparator<WorkManagerConfigBean>() {
		// // 按应用名排序
		// public int compare(WorkManagerConfigBean o1, WorkManagerConfigBean
		// o2) {
		// return o1.getAppName().compareTo(o2.getAppName());
		// }
		// });
		// log.debug("Stop sort.");
		log.debug("Stop getConfigs.");
		return buildAllWmSnapshots(tmp);
	}

	private List<WorkManagerSnapshot>  buildAllWmSnapshots(List<WorkManagerConfigBean> configs){
		if(configs == null || configs.isEmpty()){
			return Collections.emptyList();
		}
		
		List<WorkManagerSnapshot>  result = new ArrayList<WorkManagerSnapshot>();
		Map<String, List<WorkManagerConfigBean>>  map = buildMapAccordingApp(configs);
		for(String appName : map.keySet()){
			WorkManagerSnapshot wmSnapshot = new WorkManagerSnapshot(appName, map.get(appName));
			result.add(wmSnapshot);
		}
		
		return result;
	}
	
	private Map<String, List<WorkManagerConfigBean>> buildMapAccordingApp(
			List<WorkManagerConfigBean> configs) {
		if(configs == null || configs.isEmpty()){
			return Collections.emptyMap();
		}
		
		Map<String, List<WorkManagerConfigBean>> result = new HashMap<String, List<WorkManagerConfigBean>>();
		for(WorkManagerConfigBean bean : configs){
			String key = bean.getAppName();
			if(key == null || key.length() == 0){
				throw new RuntimeException("Application name of WorkManagerconfigBean[" + bean + "] cannot be null.");
			}
			
			List<WorkManagerConfigBean> list = result.get(key);
			if(list == null){
				list = new ArrayList<WorkManagerConfigBean>();
				result.put(key, list);
			}
			
			list.add(bean);
		}
		
		return result;
	}

	// private Map<WorkManagerConfigBean, Double> utilizationMap;

	/**
	 * @param set
	 * @return
	 */
	private List<WorkManagerConfigBean> kalmanFilter(List<WorkManagerConfigBean> set) {

		double cpu = WorkManagerConfigBeanFactory.getTotalCpu();

		double cpuPercent = cpu / 100D;

		double[] throughput = new double[set.size()];
		double[] utilizations = new double[set.size()];

		int i = 0;
		for (WorkManagerConfigBean bean : set) {
			throughput[i] = bean.getThroughput();
			utilizations[i] = internalCpuValue(bean.getCpuValue());

			i++;
		}

		utilizations = CPUFilter.getInstance().filter(throughput, utilizations, cpuPercent);

		i = 0;
		double[] x = CPUFilter.getInstance().getX();
		for (WorkManagerConfigBean bean : set) {
			// utilizations[i] = bean.getCpuValue();

			// utilizationMap.put(bean, utilizations[i]);

			bean.setCpuValue(utilizations[i]);
			bean.setAvgUtil(String.format("%.6f", x == null ? 0 : x[i]));

			log.debug("utilizations[i]=" + utilizations[i]);

			i++;
		}

		return set;
	}

	static List<WorkManagerConfigBean> getConfigs2() {
		// if(internalConfigs == null){
		WorkManagerConfigBean wm1 = new WorkManagerConfigBean("TestWM_App_1", "WM_1", 10,
				nextCpuValue());
		WorkManagerConfigBean wm2 = new WorkManagerConfigBean("TestWM_App_2", "WM_2", 10,
				nextCpuValue());

		List<WorkManagerConfigBean> tmp = new ArrayList<WorkManagerConfigBean>();
		tmp.add(wm1);
		tmp.add(wm2);
		internalConfigs = tmp;
		// }

		return internalConfigs;
	}

	static double nextCpuValue() {
		return rnd.nextDouble() * 100;
	}

	@OnEvent(component = "zoneUpdater")
	Object trigger() {
		return wmInfoZone.getBody();
		// return new MultiZoneUpdate("wmInfoZone", wmInfoZone.getBody());
		// return new MultiZoneUpdate("wmInfoZone", wmInfoZone.getBody());
	}

	public long getInterval() {
		return interval;
	}

	public double getCpuValue() {
		// double threadCpuValue = utilizationMap.get(config);
		double threadCpuValue = configBean.getCpuValue();
		// double threadCpuValue = config.getCpuValue();
		log.debug("Get thread CpuValue" + threadCpuValue);
		// double cpuValue = internalCpuValue(threadCpuValue) * 100;
		// log.debug("Get cpu value " + cpuValue);
		// return cpuValue;
		return threadCpuValue;
	}

	private static double internalCpuValue(double threadCpuValue) {
		return threadCpuValue / (interval * CpuUtil.getCoreNum());
	}
	
	public boolean isFirstWm(){
		return index == 0;
	}
}
