package com.onceas.wm.console.pages;

import org.apache.tapestry5.annotations.Persist;

import com.onceas.wm.console.model.User;

public class CreateUser {

	@Persist
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
