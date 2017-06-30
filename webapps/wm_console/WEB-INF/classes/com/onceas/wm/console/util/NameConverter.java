package com.onceas.wm.console.util;

public class NameConverter {
	/**
	 * 获得构造workmanagerconfig object name中的applicationName
	 * 返回中最后一个“/”开始到结尾的部分
	 * @param name
	 * @return   
	 */
	public static String getCanonicalApplicationName(String name){
		if(name == null ){
			return name;
		}
		int index = name.lastIndexOf('/');
		if(index == -1)
			return name;
		return name.substring(index);
	}
}
