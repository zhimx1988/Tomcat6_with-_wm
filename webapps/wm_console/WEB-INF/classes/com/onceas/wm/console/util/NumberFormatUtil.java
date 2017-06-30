package com.onceas.wm.console.util;

import java.text.DecimalFormat;

/**
 * @author syk
 * 
 */
public class NumberFormatUtil {

	private static final DecimalFormat df = new DecimalFormat("0.0");
	private static final String PERCENT_SYMBOLE = "%";

	/**
	 * 保留3位小数并增加%
	 */
	public static String percent(double value) {
		return df.format(value) + PERCENT_SYMBOLE;
	}
}
