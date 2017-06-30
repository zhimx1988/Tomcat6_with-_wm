package com.onceas.wm.console.util;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.onceas.wm.console.model.User;

public class UserCollection {

	public static Map<String,User> allUsers;
	static {
		allUsers = new HashMap<String, User>();
		for(int i=1; i < 4; i++){
			User user = new User();
			user.setBirthDate(new Date());
			user.setName("syk_" + i);
			user.setDescription("test_" + i);
			allUsers.put(user.getName(), user);
		}
		
	}
	
	public static Collection<User> getAllUsers(){
		return allUsers.values();
	}
	
	public static User getUserFromName(String userName){
		return allUsers.get(userName);
	}
}
