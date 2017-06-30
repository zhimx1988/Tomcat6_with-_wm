package com.onceas.wm.console.util;

public class NameConverter {
	/**
	 * ��ù���workmanagerconfig object name�е�applicationName
	 * ���������һ����/����ʼ����β�Ĳ���
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
