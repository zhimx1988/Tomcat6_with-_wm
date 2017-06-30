package com.onceas.wm.console.components;

import net.sourceforge.tdojo.TDojoEventConstants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Any;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;

import com.onceas.wm.console.model.WorkManagerConfigBean;
import com.onceas.wm.console.util.WorkManagerConfigBeanFactory;

/**
 * @author syk
 *
 */
public class WmConfigRow {

	private static final Log log = LogFactory.getLog(WmConfigRow.class);
	
	private static final String SEPERATER = "#";
	@Property
	@Parameter(required=true)
	private WorkManagerConfigBean model;
	
	@InjectComponent(value="sliderinput")
	private Any sliderinput;
	
	@Property
	private String sliderValue;
	
	@Property
	private int value;
	
	public String getSliderInputId(){
		return sliderinput.getClientId();
	}
	
	public String getWmKey(){
		return model.getAppName() + SEPERATER + model.getWmName() + SEPERATER + model.getWmType() + SEPERATER + model.getUser() + SEPERATER + model.getGroup();
	}
	
	@OnEvent(component = "tjsonrequester", value = TDojoEventConstants.JSONRequest)
	public Object onJSONRequest(JSONObject param) {
		value = Integer.parseInt(param.get("value") + "");
		String key = param.get("key")+"";
		String tmp[] =  key.split(SEPERATER);
		String appName = tmp[0];
		String wmName = tmp[1];
		String wmType = tmp[2];
		String user = tmp[3];
		String group = tmp[4];
		
		WorkManagerConfigBeanFactory.updateMaxCountOfAppWorkManager(appName,wmName,wmType,user,group, value);
		
		log.debug("new value =" + value);
		log.debug("key = " + param.get("key"));
		log.debug("Update AppWorkManager MaxConstraint value to " + value + " for application[" + appName +"]");
		return new JSONArray(param.get("value"));
	}
	
	
}
