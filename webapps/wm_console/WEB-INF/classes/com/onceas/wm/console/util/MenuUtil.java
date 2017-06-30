package com.onceas.wm.console.util;


/**
 * @author syk
 *
 */
public class MenuUtil {

	/**
	 * @return root of the menu
	 */
	public static MenuNode construct() {
		String rootvalue = "Index";
		MenuNode root = createMenuNode(rootvalue);
		root.addChild(createMenuNode("WebAppList"));
		root.addChild(createMenuNode("WorkManagerMonitor"));
		//root.addChild(createMenuNode("WorkManagerConfig"));
		//wmconfig
//		for (int i = 0; i < nodelist.size(); i++) {
//			root.addChild(nodelist.get(i));
//		}
		return root;
	}

	private static MenuNode createMenuNode(String value) {
		return new MenuNode(value, value, value);
	}

}
