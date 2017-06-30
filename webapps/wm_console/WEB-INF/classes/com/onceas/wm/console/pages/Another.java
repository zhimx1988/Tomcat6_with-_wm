package com.onceas.wm.console.pages;

import org.apache.tapestry5.annotations.OnEvent;


public class Another {
	private String userName;

	private String userPosition;

	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPosition() {
		return userPosition;
	}

	public void setUserPosition(String userPosition) {
		this.userPosition = userPosition;
	}

	public String getGreeting() {
		return "Hello Tapestry!";
	}
	
	@OnEvent(value="activate")
	void onActivate(String uName,String uPosition){
		System.out.println("Another: onActivate()");
		setUserName(uName);
		setUserPosition(uPosition);
	}
	
	@OnEvent(value="passivate")
	Object[] onPassivate(){
		System.out.println("Another: onPassivate()");
		return new Object[]{getUserName(),getUserPosition()};
	}
}
