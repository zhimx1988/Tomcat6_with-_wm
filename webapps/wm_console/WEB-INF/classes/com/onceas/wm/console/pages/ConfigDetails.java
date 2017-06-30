package com.onceas.wm.console.pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.apache.tapestry5.annotations.Property;

import com.onceas.wm.console.model.WorkManagerConfigBean;
import com.onceas.wm.console.util.ConfigUtil;
import com.onceas.wm.console.util.WorkManagerConfigBeanFactory;

/**
 * @author syk
 *
 */

@SuppressWarnings("unused")
public class ConfigDetails {

	@Property
	private long value;
	
	@Property
	private WorkManagerConfigBean config;
	
	private static List<WorkManagerConfigBean>  internalConfigs;
	
	
	public List<WorkManagerConfigBean> getWmConfigs(){
		return getConfigs();
	}
	
	private List<WorkManagerConfigBean> getConfigs(){
//			Set<WorkManagerConfigBean> set = WorkManagerConfigBeanFactory.fetchAllApplicationWorkManagerMaxValues();
		   Set<WorkManagerConfigBean> set = WorkManagerConfigBeanFactory.fetchAppWorkManagerBasicStats();
			List<WorkManagerConfigBean> result = new ArrayList<WorkManagerConfigBean>(set);
			Collections.sort(result, new Comparator<WorkManagerConfigBean>() {
				// 按应用名排序
				public int compare(WorkManagerConfigBean o1, WorkManagerConfigBean o2) {
					int compare = o1.getAppName().compareTo(o2.getAppName());
					if(compare != 0){
						return compare;
					}else{
						return o1.getWmName().compareTo(o2.getWmName());
					}
				}
			});
		return result;
	}
	
	public boolean isSimpleDisplay(){
		return ConfigUtil.isSimpleDisplay();
	}
}
