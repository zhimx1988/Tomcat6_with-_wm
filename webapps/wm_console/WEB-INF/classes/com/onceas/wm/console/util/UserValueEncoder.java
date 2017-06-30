package com.onceas.wm.console.util;

import org.apache.tapestry5.ValueEncoder;

import com.onceas.wm.console.model.User;

public class UserValueEncoder implements ValueEncoder<User> {

	public String toClient(User value) {
		return value.getName();
	}

	public User toValue(String clientValue) {
		return UserCollection.getUserFromName(clientValue);
	}
}
