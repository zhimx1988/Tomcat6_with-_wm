package com.onceas.wm.console.util;

import javax.management.ObjectName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.onceas.wm.console.pages.WorkManagerConfig;

/**
 * @author syk
 *
 */
public class WebAppInfo {
	private static final Log log = LogFactory.getLog(WebAppInfo.class);
	 public static ObjectName[] names;
	 
	  /**
	   * 查询Web Container中与应用相关的服务
	   * @return String[]
	   */
	  public static String[] listAppMBeans()
	  {
		log.debug("Start getting names .");
	    names = MServerProxy.queryMBeans("*:j2eeType=WebModule,*");
	    log.debug("names getted.");
	    String[] result = new String[names.length];
	    for (int i = 0; i < names.length; i++) {
	      result[i] = names[i].getKeyProperty("name"); //   .substring(appMBeans[i].indexOf("name")+5,
//	                                         appMBeans[i].indexOf(',',appMBeans[i].indexOf("name")));
	    }
	    log.debug("End listAppMBeans.");
	    return result;

	  }
}
