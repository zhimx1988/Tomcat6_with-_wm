package com.onceas.wm.console.util;

import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {

	public static Properties getProperties(String propertyFile) {
		if(propertyFile == null || propertyFile.length() == 0){
			throw new IllegalArgumentException("Paramerter[propertyFile] must be provided.");
		}
		Properties properties = null;
		InputStream in = PropertyLoader.class.getClassLoader()
				.getResourceAsStream(propertyFile);
		properties = new Properties();

		try {
			properties.load(in);
		} catch (Throwable e1) {
			throw new RuntimeException("Configuration properties files "
					+ propertyFile + " not found.");
		}
		return properties;
	}
	
}
