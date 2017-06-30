package com.onceas.wm.console.pages;

import static com.onceas.wm.console.util.WorkManagerUtil.buildWmContext;
import static com.onceas.wm.console.util.WorkManagerUtil.delWorkManager;
import static com.onceas.wm.console.util.WorkManagerUtil.getWorkManagersFor;
import static com.onceas.wm.console.util.WorkManagerUtil.parseContext;
import static com.onceas.wm.console.util.WorkManagerUtil.persist;

import java.util.List;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import com.onceas.wm.console.model.WorkManagerBean;
import com.onceas.wm.console.util.NameConverter;

/**
 * 给定应用的WorkManager列表
 * @author syk
 *
 */
public class WorkManagerList {

	@Property
	private String webappName;
	
	@Property
	private WorkManagerBean wmBean;
	
	@Inject
	private PageRenderLinkSource linkSource;
	
	void onActivate(String webappName)
    {
		this.webappName = NameConverter.getCanonicalApplicationName(webappName);
    }

	public List<WorkManagerBean> getWorkManagers(){
		return getWorkManagersFor(webappName);
	}
	
	public String getUpdateContext(){
		return buildContext();
	}

	public String getDeleteContext(){
		return buildContext();
	}

	private String buildContext() {
		return buildWmContext(webappName, wmBean.getName()) ;
	}
	
	private int contextLength(){
		return 2;// appName + wmName
	}
	
	/** delete operation **/
	@OnEvent(value=EventConstants.ACTION,component="delete")
	Object onDeleteAction(String context){
		String[] c = parseContext(context);
		if(c.length != contextLength()){
			throw new RuntimeException("Context passed error");
		}
		
		delWorkManager(c[0],c[1]);
		
		return redirectToWorkManagerListPage(c[0]);
	}
	
	@OnEvent(value=EventConstants.ACTION,component="persist")
	Object onPersistAction(String context){
		persist(context);
		return redirectToWorkManagerListPage(context);
	}
	
	private Object redirectToWorkManagerListPage(String context){
		// redirect to the page
		return linkSource.createPageRenderLinkWithContext("workmanagerlist", context);
	}
}
