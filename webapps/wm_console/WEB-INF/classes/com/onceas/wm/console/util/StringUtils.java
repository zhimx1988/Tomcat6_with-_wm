package com.onceas.wm.console.util;

/**
 * @author syk
 *
 */
public class StringUtils {

	
	public static boolean isEmpty(String str){
		return str == null || str.length() == 0;
	}
	
	/*
	 * display null as N/A string
	 */
	public static String displayNullAsNa(String str){
		if(str == null){
			return "N/A";
		}
		
		return str;
	}
}
