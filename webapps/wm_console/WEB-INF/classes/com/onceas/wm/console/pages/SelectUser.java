package com.onceas.wm.console.pages;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.Property;

import com.onceas.wm.console.model.User;
import com.onceas.wm.console.util.UserCollection;
import com.onceas.wm.console.util.UserSelectModel;
import com.onceas.wm.console.util.UserValueEncoder;

public class SelectUser {

	@Property
	private User selectedUser;
	
	public SelectModel getUserSelectModel(){
		return new UserSelectModel(UserCollection.getAllUsers());
	}
	
	public ValueEncoder<User> getUserEncoder(){
		return new UserValueEncoder();
	}
}
