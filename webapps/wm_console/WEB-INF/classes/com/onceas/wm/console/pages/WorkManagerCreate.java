package com.onceas.wm.console.pages;

import static com.onceas.wm.console.util.WorkManagerUtil.fetchWorkManagerBean;
import static com.onceas.wm.console.util.WorkManagerUtil.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Context;
import org.apache.tapestry5.services.PageRenderLinkSource;

import com.onceas.wm.console.model.WorkManagerBean;

/**
 * @author syk
 *
 */
public class WorkManagerCreate {

	private static final Log log = LogFactory.getLog(WorkManagerCreate.class);
	
	private static final String WM_XML_PATH = "/WEB-INF/wm.xml";
	
	@Persist(PersistenceConstants.FLASH)
	private boolean _createWorkManager;

	@Persist(PersistenceConstants.FLASH)
	@Property
	private WorkManagerBean wmBean;
	
	@Persist(PersistenceConstants.FLASH)
	private String appName;
	
	@Inject
	private PageRenderLinkSource linkSource;
	
	@Inject
	private Context c;
	
	
	void onActivate(String context)
    {
		String[] params = parseContext(context);
		int len = params.length;
		if(len == 1){// appName
			_createWorkManager = true;
			appName = params[0];
			wmBean = new WorkManagerBean();
		}else if(len ==2 ){// appName + wmName
			_createWorkManager = false;
			appName = params[0];
			log.debug("Fetch WorkManager Bean (appName=" + params[0] + ",wmName=" + params[1] + ").");
			wmBean = fetchWorkManagerBean(params[0], params[1]);
		}else{
			throw new RuntimeException("Context[" + context + "] passed error.");
		}
		
		log.debug("wmBean = " + wmBean + " in onActivate of WorkManagerCreate page.");
    }

	
	
	@OnEvent(component="wmForm", value=EventConstants.SUCCESS)
	Object onSuccess(){
		if(_createWorkManager){
			log.debug("Create WorkManager for application " + appName);
			createWorkManager(appName,wmBean);
		}else{
			log.debug("Update WorkManager for application " + appName);
//			updateWorkManager(appName,wmBean);
		}
		
		// redirect to the page
		return linkSource.createPageRenderLinkWithContext("workmanagerlist", appName);
	}

}
