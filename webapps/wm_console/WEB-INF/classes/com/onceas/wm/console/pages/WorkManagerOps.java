package com.onceas.wm.console.pages;

import static com.onceas.wm.console.util.WorkManagerUtil.createWorkManager;
import static com.onceas.wm.console.util.WorkManagerUtil.fetchWorkManagerBean;
import static com.onceas.wm.console.util.WorkManagerUtil.parseContext;

import static com.onceas.wm.console.util.WorkManagerUtil.delCapacityConstraint;
import static com.onceas.wm.console.util.WorkManagerUtil.delMaxThreadsConstraint;
import static com.onceas.wm.console.util.WorkManagerUtil.delMinThreadsConstraint;
import static com.onceas.wm.console.util.WorkManagerUtil.delFairShareRequestClass;
import static com.onceas.wm.console.util.WorkManagerUtil.delResponseTimeRequestClass;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import com.onceas.wm.console.model.WorkManagerBean;
import com.onceas.wm.console.util.WorkManagerUtil;


public class WorkManagerOps {
	
	private static final Log log = LogFactory.getLog(WorkManagerOps.class);
	
	private String userName;

	private String userPosition;

	
	private boolean delMax;
	private boolean delMin;
	private boolean delCapacity;
	private boolean delFairShare;
	private boolean delResponseTime;
	
	@Persist
	private boolean max;
	@Persist
	private boolean min;
	@Persist
	private boolean capa;
	@Persist
	private boolean fair;
	@Persist
	private boolean resp;
	
	
	@Persist
	private boolean modify;
	
	
	@Persist(PersistenceConstants.FLASH)
	private boolean _createWorkManager;

	@Persist()
	@Property
	private WorkManagerBean wmBean;
	
	private WorkManagerBean oldwmBean;
	
	@Persist()
	private String appName;
	
	@Inject
	private PageRenderLinkSource linkSource;
	
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
	
	public String getAppName(){
		return appName;
	}
	
	void onActivate(String context)
    {
		//if(context==null)return;
		String[] params = parseContext(context);
		int len = params.length;
		if(len == 1){// appName
			_createWorkManager = true;
			appName = params[0];
			wmBean = new WorkManagerBean();
			setModify(false);
		}else if(len ==2 ){// appName + wmName
			_createWorkManager = false;
			appName = params[0];
			log.debug("Fetch WorkManager Bean (appName=" + params[0] + ",wmName=" + params[1] + ").");
			wmBean = fetchWorkManagerBean(params[0], params[1]);
			
			max = isMaxExist();
			min = isMinExist();
			capa = isCapacityExist();
			fair = isOnlyFairExist();
			resp = isOnlyResponseExist();
			
			
			setModify(true);
		}else{
			throw new RuntimeException("Context[" + context + "] passed error.");
		}
		
		log.debug("wmBean = " + wmBean + " in onActivate of WorkManagerCreate page.");
    }
	
	public void setModify(boolean modify) {
		// TODO Auto-generated method stub
		this.modify = modify;
	}
	
	public boolean isModify(){
		return this.modify;
	}

	public boolean isMaxExist(){
		if(wmBean.getMaxThreadConstraintName()!=null&&wmBean.getMaxThreadConstraintName().length()!=0)
			return true;
		return false;
	}
	public boolean isMinExist(){
		if(wmBean.getMinThreadConstraintName()!=null&&wmBean.getMinThreadConstraintName().length()!=0)
			return true;
		return false;
	}
	
	public boolean isCapacityExist(){
		if(wmBean.getCapacityConstraintName()!=null&&wmBean.getCapacityConstraintName().length()!=0)
			return true;
		return false;
	}
	
	public boolean isShareAndresponseBothExist(){
		String fairshare = wmBean.getFairShareRequestClassName();
		String request = wmBean.getResponseTimeRequestClassName();
		if((fairshare==null||fairshare.length()==0)&&(request==null||request.length()==0))
			return true;
		return false;
	}
	
	public boolean isOnlyFairExist(){
		String fairshare = wmBean.getFairShareRequestClassName();
		if(fairshare!=null&&fairshare.length()>0)
		return true;
		return false;
	}
	
	public boolean isOnlyResponseExist(){
		String request = wmBean.getResponseTimeRequestClassName();
		if(request!=null&&request.length()>0)
		return true;
		return false;
	}
	


	public boolean isDelMax() {
		return delMax;
	}

	public void setDelMax(boolean delMax) {
		this.delMax = delMax;
	}

	public boolean isDelMin() {
		return delMin;
	}

	public void setDelMin(boolean delMin) {
		this.delMin = delMin;
	}

	public boolean isDelCapacity() {
		return delCapacity;
	}

	public void setDelCapacity(boolean delCapacity) {
		this.delCapacity = delCapacity;
	}

	public boolean isDelFairShare() {
		return delFairShare;
	}

	public void setDelFairShare(boolean delFairShare) {
		this.delFairShare = delFairShare;
	}

	public boolean isDelResponseTime() {
		return delResponseTime;
	}

	public void setDelResponseTime(boolean delResponseTime) {
		this.delResponseTime = delResponseTime;
	}

	//	@OnEvent(value="passivate")
//	Object[] onPassivate(){
//		System.out.println("Another: onPassivate()"+getUserName()+getUserPosition());
//		return new Object[]{getUserName(),getUserPosition()};
//	}
	void onSelectedFromChangeBTN(String context) {
		System.out.println("submit "+context);
		
	}
	
//	@OnEvent(component="wmForm", value=EventConstants.SUCCESS)
	Object onSuccess(){
		
		if(_createWorkManager){
			log.debug("Create WorkManager for application " + appName);
			createWorkManager(appName,wmBean);
		}else{
			log.debug("Update WorkManager for application " + appName);
			//updateWorkManager(appName,wmBean);
//			WorkManagerUtil.updateMaxThreadsConstraint(appName, wmBean,max);
//			WorkManagerUtil.updateMinThreadsConstraint(appName, wmBean,min);
//			WorkManagerUtil.updateCapacityConstraint(appName, wmBean,capa);
//			WorkManagerUtil.updateFairShareRequestClass(appName, wmBean,fair);
//			WorkManagerUtil.updateResponseTimeRequestClass(appName, wmBean,resp);
			
			if(isDelMax()){
				delMaxThreadsConstraint(appName,wmBean);
			}else{
				WorkManagerUtil.updateMaxThreadsConstraint(appName, wmBean,max);
			}
			if(isDelMin()){
				delMinThreadsConstraint(appName,wmBean);
			}else{
				WorkManagerUtil.updateMinThreadsConstraint(appName, wmBean,min);
			}
			if(isDelCapacity()){
				delCapacityConstraint(appName,wmBean);
			}else{
				WorkManagerUtil.updateCapacityConstraint(appName, wmBean,capa);
			}
			if(isDelFairShare()){
				delFairShareRequestClass(appName,wmBean);
			}else{
				WorkManagerUtil.updateFairShareRequestClass(appName, wmBean,fair);
			}
			if(isDelResponseTime()){
				delResponseTimeRequestClass(appName,wmBean);
			}else{
				WorkManagerUtil.updateResponseTimeRequestClass(appName, wmBean,resp);
			}
		}
		
		// redirect to the page
		return linkSource.createPageRenderLinkWithContext("workmanagerlist", appName);
	}

}
