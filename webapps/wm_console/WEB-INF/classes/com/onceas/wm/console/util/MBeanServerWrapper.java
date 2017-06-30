package com.onceas.wm.console.util;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.ObjectName;
import javax.management.ReflectionException;

public class MBeanServerWrapper {

	public MBeanServerWrapper() {
		super();
	}

	public static Object getAttribute(ObjectName objName, String attributeName){
		Object result = null;
		
		try {
			result = MServerProxy.getMBeanServer().getAttribute(objName, attributeName);
		} catch (AttributeNotFoundException e) {
			throw new RuntimeException(e);
		} catch (InstanceNotFoundException e) {
			throw new RuntimeException(e);
		} catch (MBeanException e) {
			throw new RuntimeException(e);
		} catch (ReflectionException e) {
			throw new RuntimeException(e);
		}
		
		return result;
	}
}