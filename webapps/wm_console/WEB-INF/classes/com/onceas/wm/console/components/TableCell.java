package com.onceas.wm.console.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.onceas.wm.console.util.NumberFormatUtil;

/**
 * @author syk
 * 
 */
public class TableCell {

	@Inject
	private ComponentResources resource;

	@Property
	@Parameter(defaultPrefix = BindingConstants.LITERAL, allowNull = false)
	private String name;

	@Parameter(required = true, defaultPrefix = BindingConstants.LITERAL, allowNull = false)
	private double value;

	public boolean isBoundName() {
		return resource.isBound("name");
	}

	public String getFormatedValue() {
		double percent = getPercentValue();
		return NumberFormatUtil.percent(percent < 0 ? 0 : percent > 100 ? 100 : percent);
	}
	
	public double getPercentValue(){
		return value * 100;
	}
}
