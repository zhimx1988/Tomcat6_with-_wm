package com.onceas.wm.console.pages;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.ActionLink;
import org.apache.tapestry5.corelib.components.PageLink;

public class Greeting {

	@Property
	private int value;
	
	@Property
	private int value2;
	
	@Property
	private int index;
	
	@InjectPage
	private Another another;
	
	@Component(id="gotoAnother",parameters={"page=another"})
	private PageLink gotoAnother;
	
	@Component(id="useActionLink")
	private ActionLink useActionLink;
	
	public Object[] getActionContext(){
		String[] context = new String[2];
		context[0] = "syk";
		context[1] = "engineer";
		return context;
	}
	public String getGreeting(){
		return "Hello Tapestry!";
	}
	
	@OnEvent(component="useActionLink", value="action" )
	Object useActionLink(String name, String position){
		another.setUserName(name);
		another.setUserPosition(position);
		return another;
	}
	
	
	public boolean isOdd(){
		return index % 2 != 0;
	}
	
}
