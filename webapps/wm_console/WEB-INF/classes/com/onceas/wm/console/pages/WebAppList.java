package com.onceas.wm.console.pages;

import java.util.Arrays;
import java.util.List;

import org.apache.tapestry5.annotations.Property;

import com.onceas.wm.console.util.WebAppInfo;

/**
 * @author syk
 *
 */
public class WebAppList {

	@Property
	private String webapp;
	
	public List<String> getWebappList(){
		return Arrays.asList(WebAppInfo.listAppMBeans());
	}
}
