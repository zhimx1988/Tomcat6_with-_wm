package com.onceas.wm.console.util;

import java.util.Properties;

/**
 * @author syk
 * 
 */
public class ConfigUtil {
	private static String configFile = "config.properties";
	private static final String INTERVAL_KEY = "interval";
	private static final String WOEKMANAGER_DISPLAY_TYPE_KEY = "workmanager.display.type";
	private static long interval = 2000;
	private static Boolean isSimpleDisplay = null;
	private static Properties properties = null;
	static {
		interval = Long.parseLong(getProperties().getProperty(INTERVAL_KEY));
	}

	public static Properties getProperties() {
		if (properties != null) {
			return properties;
		} else {
			properties = PropertyLoader.getProperties(configFile);
		}
		return properties;
	}

	public static long getInterval() {
		return interval;
	}
	
	public static boolean isSimpleDisplay(){
		if(isSimpleDisplay == null){
			String value = getProperties().getProperty(WOEKMANAGER_DISPLAY_TYPE_KEY);
			isSimpleDisplay = !"complex".equals(value);
		}
		
		return isSimpleDisplay.booleanValue();
	}
}
