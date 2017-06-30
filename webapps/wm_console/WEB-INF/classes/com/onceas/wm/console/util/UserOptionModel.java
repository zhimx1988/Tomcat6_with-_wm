package com.onceas.wm.console.util;

import java.util.Map;

import org.apache.tapestry5.OptionModel;

import com.onceas.wm.console.model.User;

public class UserOptionModel implements OptionModel {

	private User user;
	
	public UserOptionModel(User user) {
		super();
		this.user = user;
	}

	public Map<String, String> getAttributes() {
		return null;
	}

	public String getLabel() {
		return user.getName();
	}

	public Object getValue() {
		return user;
	}

	public boolean isDisabled() {
		return false;
	}

}
