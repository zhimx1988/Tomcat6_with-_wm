package com.onceas.wm.console.pages;

import java.util.List;

import net.sourceforge.tdojo.comodel.FisheyeBarModel;
import net.sourceforge.tdojo.components.Zone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tapestry5.ValidationTracker;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

import com.onceas.wm.console.util.DataUtil;
import com.onceas.wm.console.util.MenuUtil;

/**
 * Start page of application test.
 */
public class WorkManagerConfig {

	private static final Log log = LogFactory.getLog(WorkManagerConfig.class);

	// need to change
	// @InjectService("defaultDataStore")
	// private DataStore<MBeanCollection> dataStore ;
	// @InjectService("newDataStore")
//	@InjectSpringBean("NewDataStore")
//	private NewDataStore dataStore;

	@InjectComponent
	private Zone UIPaneZone;

	// @InjectComponent
	// private Zone tmlPaneZone;
	//
	// @InjectComponent
	// private Zone resultPaneZone;

	// @InjectComponent
	// private Zone searchFomrZone;

	@Persist()
	private String selectedPage;

	@Property
	private String filterSelectedPage;

	@Property
	private String autoCompletePage;

	// @Component(parameters = "value=prop:autoCompletePage")
	// @Mixins("Autocomplete")
	// private TextBox autoCompleteTextBox;

	@Environmental(false)
	private ValidationTracker tracker;

//	@Persist
//	private MBean selectedMBean;

	/**
	 * for test
	 */
	private boolean debugMode = false;

	public Object getMenus(){
		return MenuUtil.construct();
	}

	private Object treeNodeUpdate(Object node) {
//		selectedPage = node.getId();

		log.info("selectedPage = " + selectedPage);

		return getZoneNeedUpdate();
	}

	@OnEvent(component = "menuTree")
	Object onComponentSelected(Object node) {
		return treeNodeUpdate(node);
	}

	// public Object getSearchModel() {
	// List<OptionModel> optionModels = new ArrayList<OptionModel>();
	//
	// for (PageNameNode node : pageNameList.getPageNameList()) {
	// optionModels.add(new OptionModelImpl(node.getId(), node.getId()));
	// }
	//
	// return new SelectModelImpl(null, optionModels);
	// }

	private MultiZoneUpdate buildMultiZone(Object... args) {
		MultiZoneUpdate multiZoneUpdate = new MultiZoneUpdate((String) args[0],
				args[1]);

		for (int i = 2; i < args.length; i += 2) {
			multiZoneUpdate = multiZoneUpdate
					.add((String) args[i], args[i + 1]);
		}

		return multiZoneUpdate;
	}

	public String getViewPageName() {
		return selectedPage == null ? "" : selectedPage;
		// if(selectedPage == null){
		// return "";
		// }
		//		
		// //tmp processing
		// if(!selectedPage.contains(":")){
		// return selectedPage;
		// }else{
		// return FilterUtil.getNameProperty(selectedPage);
		// }
	}

	// @OnEvent(value = EventConstants.PROVIDE_COMPLETIONS)
	// List<String> onAutoComplete(String input) {
	// List<String> results = new ArrayList<String>();
	// for (PageNameNode node : pageNameList.getPageNameList()) {
	// if (node.getId().toLowerCase().indexOf(input.toLowerCase()) >= 0) {
	// results.add(node.getId());
	// }
	// }
	// return results;
	// }
	//
	// @OnEvent(component = "selectSubmit", value = EventConstants.SELECTED)
	// void onSelectSubmit() {
	// selectedPage = filterSelectedPage;
	// }
	//
	// @OnEvent(component = "autoCompleteSubmit", value =
	// EventConstants.SELECTED)
	// void onAutoCompleteSubmit() {
	// for (PageNameNode node : pageNameList.getPageNameList()) {
	// if (node.getId().toLowerCase().equals(autoCompletePage.toLowerCase())) {
	// selectedPage = autoCompletePage;
	// break;
	// }
	// }
	// if (selectedPage == null) {
	// tracker.recordError(autoCompleteTextBox, "Unknow page [" +
	// autoCompletePage + "]");
	// }
	// }
	//
	// @OnEvent(value = EventConstants.SUCCESS)
	// Object onSearchFormSuccess() {
	// return getZoneNeedUpdate();
	// }
	//
	// @OnEvent(value = EventConstants.FAILURE)
	// Object onSearchFormFailure() {
	// return searchFomrZone.getBody();
	// }

	private MultiZoneUpdate getZoneNeedUpdate() {
		return buildMultiZone("UIPaneZone", UIPaneZone.getBody());
		// return buildMultiZone("UIPaneZone", UIPaneZone.getBody(),
		// "tmlPaneZone", tmlPaneZone.getBody(),
		// "resultPaneZone", resultPaneZone.getBody(), "searchFomrZone",
		// searchFomrZone.getBody());
	}

	public List<FisheyeBarModel> getItems() {
		return DataUtil.buildFishEyeModel();
	}

	public String getAttrInfos() {

//		if (selectedPage == null) {
//			return "";
//		}
//
//		// log.info("selectedPage=" + selectedPage);
//		// log.info("j2eeType=" + FilterUtil.getJ2eeTypePreperty(selectedPage));
//		// log.info("MBean count in datastore when get attribute infos :" +
//		// dataStore.getLatestData().getMBeanCount());
//
//		// tmp processing
//		if (!selectedPage.contains(":")) {
//			return selectedPage;
//		} else {
//			MBean bean = null;
//
//			String buf[] = selectedPage.split("##");
//
//			String id = buf[0];
//			String mbeanname = buf[1];
//			HashMap<String, MBeanCollection> mbmap = dataStore.getLatestData();
//
//			MBeanCollection collection = mbmap.get(id);
//
//			if (debugMode) {
//				bean = DataFetcher.getMBean(mbeanname);
//			} else {
//				bean = FilterUtil.findMBean(mbeanname, collection);
//			}
//			StringBuilder builder = new StringBuilder();
//			List<MBeanAttribute> atts = bean.getAttributes();
//			for (int i = 0; i < atts.size(); i++) {
//				builder.append(atts.get(i));
//				builder.append("\n");
//			}
//			return builder.toString();
//		}
		return null;
	}

	/**
	 * @param latestData
	 */
//	private void print(MBeanCollection latestData) {
//		if (latestData == null) {
//			return;
//		}
//		for (MBean mbean : latestData.getAllMBeans()) {
//			log.info(mbean.toString());
//		}
//	}

//	public boolean isHasData() {
//		if (dataStore.getLatestData().size() > 0)
//			return true;
//		else
//			return false;
//	}

	/*
	 * public boolean isHasModelData(){ if(debugMode){ return true; } return
	 * dataStore.getLatestData() != null; }
	 * 
	 * public boolean isHasJsr77Data(){ if(debugMode){ return true; } return
	 * FilterUtil.isContainsJ2eeDomainData(dataStore.getLatestData()); }
	 */

//	public MBean getSelectedMBean() {
//		// if(selectedMBean == null){
//		selectedMBean = findMBean();
//		// }
//
//		return selectedMBean;
//	}
//
//	private MBean findMBean() {
//		log.info("selectpage = " + selectedPage);
//		if (selectedPage == null) {
//			return null;
//		}
//
//		MBean bean = null;
//		if (!selectedPage.contains(":")) {
//			// return selectedPage;
//
//		} else {
//			if (debugMode) {
//				bean = DataFetcher.getMBean(selectedPage);
//			} else {
//
//				String buf[] = selectedPage.split("##");
//
//				if (buf.length < 2)
//					return bean;
//				String id = buf[0];
//				String mbeanname = buf[1];
//				HashMap<String, MBeanCollection> mbmap = dataStore
//						.getLatestData();
//
//				MBeanCollection collection = mbmap.get(id);
//
//				if (debugMode) {
//					bean = DataFetcher.getMBean(mbeanname);
//				} else {
//					bean = FilterUtil.findMBean(mbeanname, collection);
//				}
//				StringBuilder builder = new StringBuilder();
//
//				if (bean == null)
//					return bean;
//				List<MBeanAttribute> atts = bean.getAttributes();
//				for (int i = 0; i < atts.size(); i++) {
//					builder.append(atts.get(i));
//					builder.append("\n");
//				}
//				bean.setDescription(id);
//			}
//		}
//
//		return bean;
//	}
//
//	public boolean isSelectedMBeanNull() {
//		return getSelectedMBean() == null;
//	}

	public String getSelectedPage() {
		return selectedPage;
	}

	public void setSelectedPage(String selectedPage) {
		this.selectedPage = selectedPage;
	}
}
