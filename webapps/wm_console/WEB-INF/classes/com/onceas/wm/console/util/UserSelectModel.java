package com.onceas.wm.console.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.util.AbstractSelectModel;

import com.onceas.wm.console.model.User;

public class UserSelectModel extends AbstractSelectModel {

	private Collection<User> users;
	
	public UserSelectModel(Collection<User> users) {
		super();
		this.users = users;
	}

	public List<OptionGroupModel> getOptionGroups() {
		return null;
	}

	public List<OptionModel> getOptions() {
		List<OptionModel> result = new ArrayList<OptionModel>();
		for(User user: users){
			result.add(new UserOptionModel(user));
		}
		return result;
	}
}
